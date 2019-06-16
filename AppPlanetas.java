package application;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.query.BindingSet;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AppPlanetas extends Application {

	//Janela principal
	private Stage janelaPrincipal;
	//Planetas em formato ListView
	private ListView<String> lvPlanetas;
	//Campos de texto usados para coletar dados
	TextField txtField = new TextField();
	TextField peso = new TextField();
	//Container para a cena
	VBox container = null;

	@Override
	public void start(Stage primaryStage) throws Exception {

		try {
			janelaPrincipal = primaryStage;
			
			container = new VBox();
			container.setPadding(new Insets(50, 50, 50, 50));

			//descrição da aplicação
			Text intro = new Text("Quando foram encontrados planetas fora do Sistema Solar, foram chamados \'Exoplanetas\'. "
					+ "\nEm sua maioria são Gigantes Gasos como Jupiter já que o tamanho é um dos principais fatores para"
					+ "\nse encontrar um exoplaneta. Como a estrela mais próxima de nós fica a mais ed 4 anso-luz de "
					+ "\ndistancia(a distancia que a luz percorre em um ano) não há exoplanetas próximos. Na DBpedia é"
					+ "\npossível encontrar planetas à partir de 14 anos luz. As distancias, dimensões  e propriedades"
					+ "\ndestes lugares são o objeto de analise desta aplicação. Para começar:\n");

			Label txt = new Label("Digite uma distancia em anos-luz\n");

			txtField.setMaxSize(100, 30);

			
			//Botão de busca
			Button btnSearch = new Button("Buscar Exoplanetas");
			btnSearch.setOnMouseClicked(e -> buscar());

			container.getChildren().addAll(intro, txt, txtField, btnSearch);

			Scene scene = new Scene(container);

			janelaPrincipal.setScene(scene);
			janelaPrincipal.show();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//Gera uma lista de planetas entre a terra e a distancia dada.
	private void buscar() {

		lvPlanetas = new ListView<>();
		List<String> planetas = lvPlanetas.getItems();

		double inputDist = Double.parseDouble(txtField.getText());

		List<Astro> las = new ArrayList<>();
		
		planetas.add("Distancia   -    Planeta");
		if(inputDist < 14) {
			planetas.add("Nenhum planeta dentro deste raio");
		}

		for (BindingSet bs : ConsultaBase.getPlanetasDistancia(inputDist)) {
			planetas.add(((IRI) bs.getValue("planeta")).getLocalName());
			las.add(new Astro(((IRI) bs.getValue("planeta")).getLocalName(),
					((Literal) bs.getValue("dist")).doubleValue(), ((Literal) bs.getValue("massa")).doubleValue(),
					((Literal) bs.getValue("raio")).doubleValue()));
		}

		Astro meuPlaneta = new Astro();

		for (Astro a : las) {
			if (a.getPlanetName().equals(lvPlanetas.getSelectionModel().getSelectedItem()))
				;
			meuPlaneta = a;
		}

		Double dist = meuPlaneta.getPlanetDist();
		Double massa = meuPlaneta.getPlanetMass();
		Double raio = meuPlaneta.getPlanetRadius();

		Button btnGo = new Button("Go!");
		btnGo.setOnMouseClicked(e -> processar(lvPlanetas.getSelectionModel().getSelectedItem(), dist, massa, raio));

		peso.setMaxSize(50, 30);
		Label pesoTxt = new Label("Digite seu peso para saber quanto pesaria neste planeta!");
		VBox listaDePlanetas = new VBox(new Label("Exoplanetas a até " + inputDist + " anos-luz da Terra"), lvPlanetas,
				pesoTxt, peso, btnGo);
		janelaPrincipal.setScene(new Scene(listaDePlanetas, 500, 500));

	}

	//Processa os dados do planeta bem como mostra a estrela e constelação a que pertence, quando disponíveis
	public void processar(String planeta, double dist, double massa, double raio) {

		String star = "";
		double sRaio = 0;
		String constell = "";
		int nStars = 0;
		int nPlanets = 0;

		Calculadora c = new Calculadora();

		for (BindingSet bs : ConsultaBase.getEstrela(planeta)) {
			star = ((IRI) bs.getValue("star")).getLocalName();
			sRaio = ((Literal) bs.getValue("radius")).doubleValue();
		}

		for (BindingSet bs : ConsultaBase.getConstelacao(star)) {
			constell = ((IRI) bs.getValue("constelation")).getLocalName();
			nStars = ((Literal) bs.getValue("numStars")).intValue();
			nPlanets = ((Literal) bs.getValue("numPlanets")).intValue();
		}

		Label l1 = new Label(planeta);

		Text t1 = new Text("\nEste planeta fica a " + dist + " anos-luz da Terra.\n");

		Label l2 = new Label("\nTempo para alcançar o planeta");

		DecimalFormat numberFormat = new DecimalFormat("#.00");
		Double[] valores = c.tempoDeViagem(dist);
		Text t2 = new Text("\nUm carro chegaria lá em: " + numberFormat.format(valores[0]) + " Século(s)!"
				+ "\nUm Avião Comercial chegaria lá em: " + numberFormat.format(valores[1]) + " Século(s)!"
				+ "\nUm Foguete Espacial chegaria lá em: " + numberFormat.format(valores[2]) + " Século(s)!"
				+ "\nUm Patinete Elétrico chegaria lá em: " + numberFormat.format(valores[4]) + " Século(s)!"
				+ "\nA Sonda Voyager, o objeto mais rápido feito pela humanidade chegaria lá em: "
				+ numberFormat.format(valores[3]) + " Século(s)!"
				+ "\nA Millenium Falcon de Star wars chegaria lá em: " + numberFormat.format(valores[5])
				+ " Anos(s)!" + "\nA Milano, nave dos Guardiões da Galáxia chegaria lá em: "
				+ numberFormat.format(valores[6]) + " Segundos(s)!");

		Label l3 = new Label("\nSeu peso neste planeta");
		double massaPessoa = c.massaNoPlaneta(massa, raio, Double.parseDouble(peso.getText()));
		Text t3 = new Text("Em " + planeta + " você pesaria " + numberFormat.format(massaPessoa) + " toneladas!");

		if (star == "")
			star = "[Não há dados]";
		Label l4 = new Label("\nA Estrela");
		Text t4 = new Text(planeta + " orbita a estrela " + star);
		Text t5 = new Text(
				"Se nosso Sol fosse trocado por " + star + ", este pareceria ter " + sRaio + " vez(es) seu tamanho.");

		if (constell == "")
			constell = "[Não há dados]";
		Label l5 = new Label("\nA Constelação");
		Text t6 = new Text(star + " pertence à constelação de " + constell);
		Text t7 = new Text(constell + " Possui " + nStars + " Estrelas e " + nPlanets + " Exoplanetas");

		Text t8 = new Text(
				"\n\n*Nem todos os exoplanetas encontrados na DBpedia possuem \nna base uma estrela e/ou constelação associada");

		VBox box = new VBox(l1, t1, l2, t2, l3, t3, l4, t4, t5, l5, t6, t7, t8);
		janelaPrincipal.setScene(new Scene(box, 500, 500));

	}

	public static void main(String[] args) {
		launch(args);
	}

}
