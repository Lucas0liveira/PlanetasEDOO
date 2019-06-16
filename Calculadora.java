package application;

import java.io.ObjectInputStream.GetField;

public class Calculadora {
	
	//Exoplanetas tem massas e raios dados em massas de Jupiter e raios de jupiter
	private static Double massaDeJupiter = 1898.0 * Math.pow(10, 27);
	private static double anoLuz = 9500 * Math.pow(10, 9);

	//Calcula o tempo de viagem para diferentes veículos
	public Double[] tempoDeViagem(Double dist) {

		Double[] valores = new Double[7];
		
		valores[0] = (10800228 * (dist)) / 300;
		valores[1] = (10800228 * (dist)) / 890;
		valores[2] = (10800228 * (dist)) / 28440;
		valores[3] = (10800228 * (dist)) / 278280;
		valores[4] = (10800228 * (dist)) / 25;
		valores[5] = (dist) / 1.5;
		valores[6] = (dist) / 6;
		
		return valores;

	}
	//Calcula massa de uma pessoa e um planeta de massa e raio dados
	public Double massaNoPlaneta(Double massa, Double raio, Double massap) {
		Double massakg = massa * massaDeJupiter;
		Double raiokm = raio * 69911; //raio de Jupiter
		Double aclGravidade = (6.67408 * Math.pow(10, -11) * massakg) / (Math.pow(raiokm, 2) * 9.8);
		return (massap * aclGravidade) / 1000;
	}

	

}
