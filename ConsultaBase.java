package application;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.vocabulary.FOAF;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.QueryResult;
import org.eclipse.rdf4j.query.QueryResults;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.sparql.SPARQLRepository;

public class ConsultaBase {

	static Set<Astro> listaAstros = new HashSet<>(); // Set de astros resultante das consultas em ordem

	// retorna lista de prefixos comuns
	private static String getPrefixes() {
		String prefixes = "";
		prefixes += "PREFIX foaf: <" + FOAF.NAMESPACE + "> \n";
		prefixes += "PREFIX rdf: <" + RDF.NAMESPACE + "> \n";
		prefixes += "PREFIX rdfs: <" + RDFS.NAMESPACE + "> \n";
		prefixes += "PREFIX dbo: <http://dbpedia.org/ontology/> \n";
		prefixes += "PREFIX dbr: <http://dbpedia.org/resource/> \n";
		prefixes += "PREFIX dct: <http://purl.org/dc/terms/> \n";
		prefixes += "PREFIX dbc: <http://dbpedia.org/resource/Category:> \n";
		return prefixes;
	}

	// retorna lista de Planetas e suas distancias
	public static List<BindingSet> getPlanetasDistancia(double x) {
		Repository repositorio = new SPARQLRepository("http://dbpedia.org/sparql");
		repositorio.init();

		try (RepositoryConnection coneccao = repositorio.getConnection()) {
			String queryString = getPrefixes();

			queryString += "SELECT ?type ?planeta ?dist ?raio ?massa WHERE {";
			queryString += "?planeta  <http://dbpedia.org/property/distLy> ?dist FILTER(?dist < " + x + ").";
			queryString += "?planeta  rdf:type ?type	FILTER(?type = <http://dbpedia.org/ontology/Planet>).";
			queryString += "?planeta  dbp:radius ?raio FILTER( datatype( ?raio) = <http://www.w3.org/2001/XMLSchema#double>).";
			queryString += "?planeta  dbp:mass ?massa   FILTER( datatype( ?massa) = <http://www.w3.org/2001/XMLSchema#double>).}";

			TupleQuery query = coneccao.prepareTupleQuery(queryString);

			try (TupleQueryResult result = query.evaluate()) {
				return QueryResults.asList(result);
			}
		}
	}

	// retorna estrela de um planeta
	public static List<BindingSet> getEstrela(String planeta) {
		Repository repositorio = new SPARQLRepository("http://dbpedia.org/sparql");
		repositorio.init();

		try (RepositoryConnection coneccao = repositorio.getConnection()) {
			String queryString = getPrefixes();

			queryString += "SELECT ?star ?radius WHERE{";
			queryString += "dbr:" + planeta + " dbp:star ?star.";
			queryString += "?star dbp:radius ?radius FILTER( datatype( ?radius) = <http://www.w3.org/2001/XMLSchema#double>).}";

			TupleQuery query = coneccao.prepareTupleQuery(queryString);

			try (TupleQueryResult result = query.evaluate()) {
				return QueryResults.asList(result);
			}
		}
	}

	// retorna Constelação de uma Estrela
	public static List<BindingSet> getConstelacao(String estrela) {
		Repository repositorio = new SPARQLRepository("http://dbpedia.org/sparql");
		repositorio.init();

		try (RepositoryConnection coneccao = repositorio.getConnection()) {
			String queryString = getPrefixes();

			queryString += "SELECT ?constelation ?numStars ?numPlanets WHERE{";
			queryString += "dbr:" + estrela + " dbp:constell ?constelation.";
			queryString += "?constelation dbp:numbermainstars ?numStars."; // num de estrelas
			queryString += "?constelation dbp:numberstarsplanets ?numPlanets.}"; // num de estrelas com planetas

			TupleQuery query = coneccao.prepareTupleQuery(queryString);

			try (TupleQueryResult result = query.evaluate()) {
				return QueryResults.asList(result);
			}
		}
	}

	// retorna Constelação de uma Estrela
	public static List<BindingSet> getAbstract(String objeto) {
		Repository repositorio = new SPARQLRepository("http://dbpedia.org/sparql");
		repositorio.init();

		try (RepositoryConnection coneccao = repositorio.getConnection()) {
			String queryString = getPrefixes();

			queryString += "SELECT ?abstract WHERE{";
			queryString += "<" + objeto + "> dbo:abstract ?abstract  FILTER(langMatches(lang(?abstract),\"en\")).}";

			TupleQuery query = coneccao.prepareTupleQuery(queryString);

			try (TupleQueryResult result = query.evaluate()) {
				return QueryResults.asList(result);
			}
		}
	}

}
