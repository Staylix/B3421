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
// type Graph::M�thode ( liste des param�tres )
// Algorithme :
//
//{
//} //----- Fin de M�thode

void Graph::afficherMap()
{
	cout << "Voici la unordered_map graph : " << endl;
	for (unordered_map<string, int>::iterator it = graph.begin(); it != graph.end(); ++it)
	{
		cout << "( " << it->first << " , " << it->second << " ) " << endl;
	}
	cout << "Voici la unordered_map passage : "<<endl;
	for (unordered_map<string, int>::iterator i = passage.begin(); i != passage.end(); ++i)
	{
		cout << "( " << i->first << " , " << i->second << " ) " << endl;
	}
	cout << "Voici la multimap top10 : "<<endl;
	for (multimap<int, string>::iterator ite = top10.begin(); ite != top10.end(); ++ite)
	{
		cout << "( " << ite->second << " , " << ite->first << " ) " << endl;
	}

}

void Graph::add(string ref, string hit)
{
	pair<unordered_map<string, int>::iterator, bool>  retour = graph.insert(make_pair(ref + " " + hit, 1));
	if (!retour.second)
	{
		graph[ref + " " + hit] = graph[ref + " " + hit] + 1;
	}
	retour = passage.insert(make_pair(hit, 1));
	if (!retour.second)
	{
		passage[hit] = passage[hit] + 1;
	}
}


void Graph::createTop10()
{
	for (unordered_map<string, int>::iterator it = passage.begin(); it != passage.end(); ++it)
	{
		top10.insert(make_pair(it->second, it->first));
	}
}


void Graph::createDotFile(string nomFichier)
{
	ofstream dot;
	dot.open(nomFichier, std::ofstream::out | std::ofstream::trunc);
	int i = 0;
	dot << "digraph {\n";
	for (unordered_map<string, int>::iterator it = passage.begin(); it != passage.end(); ++it)
	{
		dot<<"node" << i << " [label=\"" << it->first << "\"];\n";
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
		aecrire+="\"];\n";
		dot << aecrire;
	}
	dot.write("}",1);
	dot.close();
}

void Graph::afficherTop10()
{
	int compteur = 0;
	for (multimap<int, string>::reverse_iterator it = top10.rbegin(); it != top10.rend() || compteur == 10; --it, ++compteur)
	{
		cout << it->second << " (" << it->first << " hits)" << endl;
	}

}

//-------------------------------------------- Constructeurs - destructeur

Graph::Graph()
// Algorithme :
//
{
#ifdef MAP
	cout << "Appel au constructeur de <Graph>" << endl;
#endif
} //----- Fin de Graph


Graph::~Graph()
// Algorithme :
//
{
#ifdef MAP
	cout << "Appel au destructeur de <Graph>" << endl;
#endif
} //----- Fin de ~Graph


  //------------------------------------------------------------------ PRIVE

  //----------------------------------------------------- M�thodes prot�g�es
