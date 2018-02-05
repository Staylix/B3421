/*************************************************************************
Graph  -  description
-------------------
d�but                : 09/01/2018
copyright            : (C) 2018 par B3421
e-mail               : safia.el-bayed@insa-lyon.fr
gregoire.gentil@insa-lyon.fr
*************************************************************************/

//---------- R�alisation de la classe <Graph> (fichier Graph.cpp) ------------

//---------------------------------------------------------------- INCLUDE

//-------------------------------------------------------- Include syst�me
using namespace std;
#include <iostream>
#include <fstream>
#include <sstream>

//------------------------------------------------------ Include personnel
#include "Graph.h"

//------------------------------------------------------------- Constantes

//----------------------------------------------------------------- PUBLIC

//----------------------------------------------------- M�thodes publiques

//Algorithme :
//Insère la paire "referer_hit"-0 dans graph si la clé n'existait pas avant.
//Ecrase la valeur précedente en l'incrémentant de 1 si la clé existait avant.
void Graph::addGraph(string ref, string hit)
{
	pair<unordered_map<string, int>::iterator, bool>  retour = graph.insert(make_pair(ref + " " + hit, 1));
	if (!retour.second)
	{
		graph[ref + " " + hit] = graph[ref + " " + hit] + 1;
	}
}

//Algorithme :
//Insère la paire "hit"-1 dans graph si la clé n'existait pas avant.
//Ecrase la valeur précedente en l'incrémentant de 1 si la clé existait avant.
//Insère la paire "referer"-0 dans graph si la clé n'existait pas avant.
void Graph::addTop10(string ref, string hit) {
	pair<unordered_map<string, int>::iterator, bool>  retour=passage.insert(make_pair(hit, 1));
	if (!retour.second)
	{
		passage[hit] = passage[hit] + 1; //On écrase la valeur si le hit existait déjà.
	}
	passage.insert(make_pair(ref, 0));//Insertion du referer s'il n'existait pas déjà.
}

//Algorithme:
//Permet de passer du graph au top10 en regroupant chaque hit en une clé,
//et en réalisant la somme de ses visites.
void Graph::createTop10()
{
	for (unordered_map<string, int>::iterator it = passage.begin(); it != passage.end(); ++it)
	{
		top10.insert(make_pair(it->second, it->first));
		//Inversement de la clé et de la valeur pour remplir le classement.
	}
}

//Algorithme:
//Dans un premier temps, enumère tout les hits et leur attribue des numéros de noeuds
//en utilisant la map passage et en insérant le tout dans registry.
//Par la suite, parcourt graph et met en place les arcs.
//Tout cela est fait en respecant la syntaxe de GraphViz.
bool Graph::createDotFile(string nomFichier)
{
	ofstream dot;
	dot.open(nomFichier, std::ofstream::out | std::ofstream::trunc);
	if (dot)
	{
		int i = 0;
		dot << "digraph {\n";
		for (unordered_map<string, int>::iterator it = passage.begin(); it != passage.end(); ++it)
		{
			dot << "node" << i << " [label=\"" << it->first << "\"];\n";
			registry.insert(make_pair(it->first, i));
			++i;
		}
		for (unordered_map<string, int>::iterator it = graph.begin(); it != graph.end(); ++it)
		{
			stringstream s(it->first);
			string hit;
			string referer;
			getline(s, referer, ' ');
			getline(s, hit);
			int indexRef = registry[referer];
			int indexHit = registry[hit];
			string aecrire = "node";
			aecrire += to_string(indexRef);
			aecrire += " -> node";
			aecrire += to_string(indexHit);
			aecrire += " [label=\"";
			aecrire += to_string(it->second);
			aecrire += "\"];\n";
			dot << aecrire;
		}
		dot.write("}", 1);
		dot.close();
		return true;
	}
	else
	{
		return false;
	}

}

//Algorithme :
// Parcours la multipmap top10 en commencant par le dernier élément
// et affiche les 10 documents les plus visités.
// Si la map est vide, renvoie un message sur la sortie standard.
// Si plusieurs documents sont à égalité, n'affiche que les 10 premiers.
void Graph::afficherTop10()
{
	int compteur = 0;
	for (multimap<int, string>::reverse_iterator it = top10.rbegin(); it != top10.rend() && compteur< 10; ++it, ++compteur)
	{
		cout << it->second << " (" << it->first << " hits)" << endl;
	}
	if(compteur==0){
		cout << "The ranking is empty."<<endl;
	}
}

//-------------------------------------------- Constructeurs - destructeur

Graph::Graph()
#ifdef MAP
	cout << "Appel au constructeur de <Graph>" << endl;
#endif
} //----- Fin de Graph


Graph::~Graph()
{
#ifdef MAP
	cout << "Appel au destructeur de <Graph>" << endl;
#endif
} //----- Fin de ~Graph
