/*************************************************************************
-------------------------- Graph  -  description -------------------------
d�but                : 09/01/2018
copyright            : (C) 2018 par B3421
e-mail               : safia.el-bayed@insa-lyon.fr
gregoire.gentil@insa-lyon.fr
*************************************************************************/
//-------- Interface de la classe <Graph> (fichier Graph.h) ---------------
#if ! defined ( Graph_H )
#define Graph_H

//--------------------------------------------------- Interfaces utilis�es
#include <string>
#include <unordered_map>
#include <map>
using namespace std;

//------------------------------------------------------------------------
// R�le de la classe <Graph>
//  La classe Graph a pour r�le la gestion des structures de donn�es
//  Elle manipule une ordered_map pour g�rer le graphe du fichier
//  et une multimap qui permet d'acc�der rapidement aux dix hit les plus
//  importants
//------------------------------------------------------------------------

class Graph
{
	//------------------------------------------------------------ PUBLIC
public:
	//----------------------------------------------- M�thodes publiques

	void addTop10(string referer, string hit);
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

	bool createDotFile(string nomFichier);
	// Mode d'emploi :
	//  Cette m�thode permet de creer le fichier .dot utilis� par GraphViz
	//  � partir du graphe
	// Contrat :
	//  Aucun
	void afficherTop10();

	//-------------------------------------- Constructeurs - destructeur
	Graph();
	// Mode d'emploi :
	// Aucune ction.

	virtual ~Graph();
	// Mode d'emploi :
	// Aucune action.

	//----------------------------------------------------------- PRIVE
protected:

	//---------------------------------------------- Attributs prot�g�s
	unordered_map <string, int> graph;
	/* Table de hachage non ordonnée et sans doublons représentant le graphique
	 * du parcours de l'utilisateur.
	 * La clé est une concaténation du referer et du hit séparés par un espace.
	 * La valeur est le nombre de fois que la requête du referer vers le hit
	 * a été faite. */
	unordered_map <string, int> passage;
	/* Table de hachage non ordonnée et sans doublons permettant le passage
	 * de la structure du graphe à la structure du top 10.
	 * Permet aussi le listing de tous les documents afin de créer les noeuds
	 * correspondant dans le fichier .dot en sortie.
	 * La clé est le nom du document.
	 * La valeur est le nombre de visites du document.*/
	multimap <int, string> top10;
	/* Table de hachage ordonnée et avec doublons, représentant le classement
	 * par ordre croissant des documents en fonction du nombre de visites
	 * qu'ils ont eu.
	 * La clé est le nombre de visites du document.
	 * La valeur est le nom du document.*/
	unordered_map<string, int>registry;
	/* Table de hachage non ordonnée et sans doublons contenant la correspondance
	 * entre les noms des documents (les referer et les hits ) et leurs numéros
	 * de noeuds dans le graphe afin de pouvoir créer les arcs après après
	 * listé tous les noeuds.
	 * La clé est le nom du document.
	 * La valeur est son numéro de noeud.*/

};

#endif // Graph_H
#pragma once