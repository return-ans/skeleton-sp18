public class NBody {

	public static double readRadius(String fileName) {
		In in = new In(fileName);
		int firstItemInFile = in.readInt();
		double secondItemInFile = in.readDouble();
		return secondItemInFile;
	}

	/*
		extract the information from the file
	 */
	public static Planet[] readPlanets(String fileName) {
		In in = new In(fileName);
		int firstItemInFile = in.readInt(); //the number of planets
		double secondItemInFile = in.readDouble();
		Planet[] retList=new Planet[firstItemInFile]; //the list of planets
		for(int i=0;i<firstItemInFile;i++) {
			double x=in.readDouble();
			double y=in.readDouble();
			double xv=in.readDouble();
			double yv=in.readDouble();
			double m=in.readDouble();
			String img=in.readString();
			//construct a planet from this file
			Planet tmp=new Planet(x,y,xv,yv,m,img);
			//put this planet into the list
			retList[i]=tmp;
		}
		return retList;
	}

	public static void main(String[] args) {

		double T=Double.parseDouble(args[0]);
		double dt=Double.parseDouble(args[1]);
		String filename=args[2];

		double uniRadius=NBody.readRadius(filename);
		Planet[] Planets=NBody.readPlanets(filename);

		//Draw the background
		StdDraw.setScale(-uniRadius,uniRadius);
		StdDraw.clear();
		StdDraw.picture(0, 0, "images/starfield.jpg");

		//Draw the planets from the list
		for(Planet p:Planets) {
			p.draw();
		}

		//Create an animation by continual frames
		StdDraw.enableDoubleBuffering();
		//total time: T  interval time: dt
		for(double t=0;t<=T;t+=dt) {

			//In this frame
			double[] xForces=new double[Planets.length];
			double[] yForces=new double[Planets.length];

			for(int i=0;i<Planets.length;i++) {

				xForces[i]=Planets[i].calcNetForceExertedByX(Planets);
				yForces[i]=Planets[i].calcNetForceExertedByY(Planets);
			}

			//Update the position of planets since the forces by other planets
			for(int i=0;i<Planets.length;i++) {
				Planets[i].update(dt, xForces[i], yForces[i]);
			}

			//Draw the background in this frame
			StdDraw.picture(0, 0, "images/starfield.jpg");

			//Draw the planets with new positions int this frame
			for(Planet p:Planets) {
				p.draw();
			}
			StdDraw.show();
			StdDraw.pause(1);
		}

		StdOut.printf("%d\n", Planets.length);
		StdOut.printf("%.2e\n", uniRadius);
		for (int i = 0; i < Planets.length; i++) {
		    StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
		                  Planets[i].xxPos, Planets[i].yyPos, Planets[i].xxVel,
		                  Planets[i].yyVel, Planets[i].mass, Planets[i].imgFileName);
		}

		
	}

}
