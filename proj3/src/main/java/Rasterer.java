import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    public static final int TILE_SIZE = 256;
    // boundary, and ignore the region which is out of the ROOT
    public static final double ROOT_ULLAT = 37.892195547244356, ROOT_ULLON = -122.2998046875,
            ROOT_LRLAT = 37.82280243352756, ROOT_LRLON = -122.2119140625;
    private static final Double[] LONDPP_OF_EACH_DEPTH = {
            0.00034332275390625,
            0.000171661376953125,
            0.0000858306884765625,
            0.00004291534423828125,
            0.000021457672119140625,
            0.000010728836059570312,
            0.000005364418029785156,
            0.000002682209014892578
    };
    private int depth;
    private int k; // the number of tiles in a direction, k=2^d
    private Double q_ullon;
    private Double q_ullat;
    private Double q_lrlon;
    private Double q_lrlat;
    private Double q_w;
    private Double q_h;
    private Double q_LonDPP;
    private Double actLeftLon;
    private Double actRightLon;
    private Double actUpLat;
    private Double actLowLat;
    private Double dLon; // total_Lon/k
    private Double dLat;
    private int ulLonIndex;
    private int ulLatIndex;
    private int lrLonIndex;
    private int lrLatIndex;
    private int rowNum;
    private int colNum;
    private Double raster_ul_lon;
    private Double raster_ul_lat;
    private Double raster_lr_lon;
    private Double raster_lr_lat;

    public Rasterer() {
        // YOUR CODE HERE
    }

    /**
     * Caculate the LonDPP
     *
     * @param lrlon
     * @param ullon
     * @param width
     * @return
     */
    private Double lonDPP(Double lrlon, Double ullon, Double width) {
        return (lrlon - ullon) / width;
    }

    /**
     * find the proper depth index that LonDPP matches the query box
     *
     * @param q
     * @return
     */
    private int properLonDPP(Double q) {
        for (int i = 0; i < 8; i++) {
            if (LONDPP_OF_EACH_DEPTH[i].compareTo(q) <= 0) {
                return i;
            }
        }
        return 7;
    }

    private int upLatIndex() {
        return (int) Math.floor((ROOT_ULLAT - actUpLat) / dLat);
    }

    private int lowLatIndex() {
        return (int) Math.floor((ROOT_ULLAT - actLowLat) / dLat);
    }

    private int leftLonIndex() {
        return (int) Math.floor((actLeftLon - ROOT_ULLON) / dLon);
    }

    private int rightLonIndex() {
        return (int) Math.floor((actRightLon - ROOT_ULLON) / dLon);
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     * <p>
     * The grid of images must obey the following properties, where image in the
     * is referred to as a "tile".
     * <ul>
     *     <li>The tiles collected must cover the most longitudinal distance per pixel
     *     (LonDPP) possible, while still covering less than or equal to the amount of
     *     longitudinal distance per pixel in the query box for the user viewport size. </li>
     *     <li>Contains all tiles that intersect the query bounding box that fulfill the
     *     above condition.</li>
     *     <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     * </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     * forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        // System.out.println(params);
        q_ullon = params.get("ullon");
        q_ullat = params.get("ullat");
        q_lrlon = params.get("lrlon");
        q_lrlat = params.get("lrlat");
        q_w = params.get("w");
        q_h = params.get("h");
        q_LonDPP = lonDPP(q_lrlon, q_ullon, q_w);
        depth = properLonDPP(q_LonDPP);
        k = 1 << depth;
        dLon = (ROOT_LRLON - ROOT_ULLON) / (double) k;
        dLat = (ROOT_ULLAT - ROOT_LRLAT) / (double) k;
        // actual size
        actLeftLon = Math.max(ROOT_ULLON, q_ullon);
        actRightLon = Math.min(ROOT_LRLON, q_lrlon);
        actUpLat = Math.min(ROOT_ULLAT, q_ullat);
        actLowLat = Math.max(ROOT_LRLAT, q_lrlat);

        // calculate the start and end indices of tiles
        ulLonIndex = leftLonIndex(); // the first tile in upper_left
        ulLatIndex = upLatIndex();
        lrLonIndex = rightLonIndex(); // the last tile in lower_right
        lrLatIndex = lowLatIndex();
        rowNum = lrLatIndex - ulLatIndex;
        colNum = lrLonIndex - ulLonIndex;
        raster_ul_lon = ROOT_ULLON + (double) ulLonIndex * dLon;
        raster_ul_lat = ROOT_ULLAT - (double) ulLatIndex * dLat;
        raster_lr_lon = ROOT_ULLON + (double) (lrLonIndex + 1) * dLon;
        raster_lr_lat = ROOT_ULLAT - (double) (lrLatIndex + 1) * dLat;

        // calculate the render_grid
        String[][] render_grid = new String[rowNum + 1][colNum + 1];
        for (int r = 0; r < rowNum + 1; r++) {
            for (int c = 0; c < colNum + 1; c++) {
                int col = c + ulLonIndex;
                int row = r + ulLatIndex;
                render_grid[r][c] = "d" + depth + "_x" + col + "_y" + row + ".png";
            }
        }

        Map<String, Object> results = new HashMap<>();
        // fill the result Map
        results.put("render_grid", render_grid);
        results.put("raster_ul_lon", raster_ul_lon);
        results.put("raster_ul_lat", raster_ul_lat);
        results.put("raster_lr_lon", raster_lr_lon);
        results.put("raster_lr_lat", raster_lr_lat);
        results.put("depth", depth);
        results.put("query_success", true);

        return results;
    }

}
