package application;

public class Astro {

	String planetName;
	Double planetDist;
	Double planetMass;
	Double planetRadius;

	String starName;
	Double starRadius;

	String constelName;
	int numStars;
	int numPlanets;

	public Astro(String planetName, Double planetDist, Double planetMass, Double planetRadius) {
		this.planetName = planetName;
		this.planetDist = planetDist;
		this.planetMass = planetMass;
		this.planetRadius = planetRadius;
	}

	public Astro() {

	}
	// GETTERS E SETTERS

	public String getPlanetName() {
		return planetName;
	}

	public void setPlanetName(String planetName) {
		this.planetName = planetName;
	}

	public Double getPlanetDist() {
		return planetDist;
	}

	public void setPlanetDist(Double planetDist) {
		this.planetDist = planetDist;
	}

	public Double getPlanetMass() {
		return planetMass;
	}

	public void setPlanetMass(Double planetMass) {
		this.planetMass = planetMass;
	}

	public Double getPlanetRadius() {
		return planetRadius;
	}

	public void setPlanetRadius(Double planetRadius) {
		this.planetRadius = planetRadius;
	}

	public String getStarName() {
		return starName;
	}

	public void setStarName(String starName) {
		this.starName = starName;
	}

	public Double getStarRadius() {
		return starRadius;
	}

	public void setStarRadius(Double starRadius) {
		this.starRadius = starRadius;
	}

	public String getConstelName() {
		return constelName;
	}

	public void setConstelName(String constelName) {
		this.constelName = constelName;
	}

	public int getNumStars() {
		return numStars;
	}

	public void setNumStars(int numStars) {
		this.numStars = numStars;
	}

	public int getNumPlanets() {
		return numPlanets;
	}

	public void setNumPlanets(int numPlanets) {
		this.numPlanets = numPlanets;
	}

}
