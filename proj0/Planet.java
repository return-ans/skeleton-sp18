public class Planet {
	public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

	/**
	 * constructed by params
	 * @param xP
	 * @param yP
	 * @param xV
	 * @param yV
	 * @param m
	 * @param img
	 */
	public Planet(double xP, double yP, double xV,double yV, double m, String img) {
		this.xxPos=xP;
		this.yyPos=yP;
		this.xxVel=xV;
		this.yyVel=yV;
		this.mass=m;
		this.imgFileName=img;
	}

	/**
	 * consturcted by coping p
	 * @param p
	 */
	public Planet(Planet p) {
		this.xxPos=p.xxPos;
		this.yyPos=p.yyPos;
		this.xxVel=p.xxVel;
		this.yyVel=p.yyVel;
		this.mass=p.mass;
		this.imgFileName=p.imgFileName;
	}
	
	public double calcDistance(Planet other) {
		double dx=Math.abs(this.xxPos-other.xxPos);
		double dy=Math.abs(this.yyPos-other.yyPos);
		double tmp=Math.pow(dx, 2)+Math.pow(dy, 2);
		double ret=Math.sqrt(tmp);
		return ret;
	}

	/**
	 * Pay attention that if the DISTANCE between two planets is ZERO!!!!!!
	 * @param other
	 * @return
	 */
	public double calcForceExertedBy(Planet other) {
		//Note divided by zero
		//In the same place, distance is ZERO, return 0.00 directly!!!!!
		if(this.xxPos==other.xxPos&&this.yyPos==other.yyPos) return 0.00000;

		double G=6.67e-11;
		double dis=calcDistance(other);
		double ret=(G*this.mass*other.mass)/Math.pow(dis,2);
		return ret;
	}
	
	public double calcForceExertedByX(Planet other) {
		//Note divided by zero
		if(this.xxPos==other.xxPos&&this.yyPos==other.yyPos) return 0.00000;

		double dis=calcDistance(other);
		double F=calcForceExertedBy(other);
		double dx=other.xxPos-this.xxPos;
		double ret=F*dx/dis;
		return ret;
	}

	/**
	 * Pay attention!!!! Divided by ZERO!!!!
	 * @param other
	 * @return
	 */
	public double calcForceExertedByY(Planet other) {
		//Note divided by zero
		if(this.xxPos==other.xxPos&&this.yyPos==other.yyPos) return 0.00000;

		double dis=calcDistance(other);
		double F=calcForceExertedBy(other);
		double dy=other.yyPos-this.yyPos;
		double ret=F*dy/dis;
		return ret;
	}
	
	public double calcNetForceExertedByX(Planet[] planetsList) {
		double ret=0.00;
		for(Planet p:planetsList) {
			ret += calcForceExertedByX(p);
		}
		return ret;
	}
	
	public double calcNetForceExertedByY(Planet[] planetsList) {
		double ret=0.00;
		for(Planet p:planetsList) {
			// if compare itself, continue
			if (this.imgFileName == p.imgFileName) continue;
			ret += calcForceExertedByY(p);
		}
		return ret;
	}
	
	public void update(double dt,double fX,double fY) {
		double ax=fX/this.mass;
		double ay=fY/this.mass;
		double newVx=this.xxVel+dt*ax;
		double newVy=this.yyVel+dt*ay;
		double newPx=this.xxPos+dt*newVx;
		double newPy=this.yyPos+dt*newVy;
		this.xxPos=newPx;
		this.yyPos=newPy;
		this.xxVel=newVx;
		this.yyVel=newVy;
	}
	
	public void draw() {
	    StdDraw.picture(this.xxPos, this.yyPos, "images/"+this.imgFileName);
	}

}
