/*************************************************************************
Graph  -  description
-------------------
d�but                : 09/01/2018
copyright            : (C) 2018 par B3421
e-mail               : safia.el-bayed@insa-lyon.fr
gregoire.gentil@insa-lyon.fr
*************************************************************************/

//---------- Interface de la classe <Graph> (fichier Graph.h) ----------------
#if ! defined ( Graph_H )
#define Graph_H

//--------------------------------------------------- Interfaces utilis�es
#include <string>
#include <unordered_map>
#include <map>
using namespace std;
//------------------------------------------------------------- Constantes

//------------------------------------------------------------------ Types

//------------------------------------------------------------------------
// R�le de la classe <Graph>
//  La classe Graph a pour r�le la gestion des structures de donn�es
//  Elle manipule une ordered_map pour g�rer le graphe du fichier
//  et une multimap qui permet d'acc�der rapidement aux dix hit les plus
//  importants
//
//------------------------------------------------------------------------

class Graph
{
	//----------------------------------------------------------------- PUBLIC

public:
	//----------------------------------------------------- M�thodes publiques
	
	void addTop10(string ref, string hit);
	void addGraph(string referer, string hit);
	// Mode d'emploi :
	//  Cette m�thode permet d'ajouter un couple cl� valeur au graphe
	//  On l'ajoute au graph ainsi qu'a la map de passage
	//  Pour graph, la cl� est la concat�nation du referer et du hit
	//  Pour passage, la cl� est le hit
	// Contrat :
	//  Aucun

	void createTop10();
	// Mode d'emploi :
	//  Cette m�thode permet de creer la multimap top10 apr�s que le graphe
	//  soit enti�rement cr�� � partir du fichier journal
	// Contrat :
	//  Aucun

	bool createDotFile(string);
	// Mode d'emploi :
	//  Cette m�thode permet de creer le fichier .dot utilis� par GraphViz
	//  � partir du graphe
	// Contrat :
	//  Aucun
	void afficherTop10();

	//-------------------------------------------- Constructeurs - destructeur

	Graph();
	// Mode d'emploi :
	//
	// Contrat :
	//

	virtual ~Graph();
	// Mode d'emploi :
	//
	// Contrat :
	//

	//------------------------------------------------------------------ PRIVE

protected:
	//----------------------------------------------------- M�thodes prot�g�es

	//----------------------------------------------------- Attributs prot�g�s
	unordered_map <string, int> graph;
	unordered_map <string, int> passage;
	multimap <int, string> top10;
	unordered_map<string, int>registry;

};

//-------------------------------- Autres d�finitions d�pendantes de <Graph>

#endif // Graph_H
#pragma once
