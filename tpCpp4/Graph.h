/*************************************************************************
Graph  -  description
-------------------
début                : 09/01/2018
copyright            : (C) 2018 par B3421
e-mail               : safia.el-bayed@insa-lyon.fr
gregoire.gentil@insa-lyon.fr
*************************************************************************/

//---------- Interface de la classe <Graph> (fichier Graph.h) ----------------
#if ! defined ( Graph_H )
#define Graph_H

//--------------------------------------------------- Interfaces utilisées
#include <string>
#include <unordered_map>
#include <map>
using namespace std;
//------------------------------------------------------------- Constantes

//------------------------------------------------------------------ Types

//------------------------------------------------------------------------
// Rôle de la classe <Graph>
//  La classe Graph a pour rôle la gestion des structures de données
//  Elle manipule une ordered_map pour gérer le graphe du fichier
//  et une multimap qui permet d'accéder rapidement aux dix hit les plus
//  importants
//
//------------------------------------------------------------------------

class Graph
{
	//----------------------------------------------------------------- PUBLIC

public:
	//----------------------------------------------------- Méthodes publiques
	void afficherMap();
	void add(string referer, string hit);
	// Mode d'emploi :
	//  Cette méthode permet d'ajouter un couple clé valeur au graphe
	//  On l'ajoute au graph ainsi qu'a la map de passage
	//  Pour graph, la clé est la concaténation du referer et du hit
	//  Pour passage, la clé est le hit
	// Contrat :
	//  Aucun

	void createTop10();
	// Mode d'emploi :
	//  Cette méthode permet de creer la multimap top10 après que le graphe
	//  soit entièrement créé à partir du fichier journal
	// Contrat :
	//  Aucun

	void createDotFile(string);
	// Mode d'emploi :
	//  Cette méthode permet de creer le fichier .dot utilisé par GraphViz
	//  à partir du graphe
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
	//----------------------------------------------------- Méthodes protégées

	//----------------------------------------------------- Attributs protégés
	unordered_map <string, int> graph;
	unordered_map <string, int> passage;
	multimap <int, string> top10;
	unordered_map<string, int>registry;

};

//-------------------------------- Autres définitions dépendantes de <Graph>

#endif // Graph_H
#pragma once
