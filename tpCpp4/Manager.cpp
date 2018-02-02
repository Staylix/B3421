/*************************************************************************
Manager  -  description
-------------------
début                : 09/01/2018
copyright            : (C) 2018 par B3421
e-mail               : safia.el-bayed@insa-lyon.fr
gregoire.gentil@insa-lyon.fr
*************************************************************************/

//---------- Réalisation du module <Manager> (fichier Manager.cpp) ---------------

/////////////////////////////////////////////////////////////////  INCLUDE
//-------------------------------------------------------- Include système
using namespace std;
#include <string>
#include <iostream>

//------------------------------------------------------ Include personnel
#include "Manager.h"
#include "logstream.h"
#include "Graph.h"

///////////////////////////////////////////////////////////////////  PRIVE
//------------------------------------------------------------- Constantes

//------------------------------------------------------------------ Types

//---------------------------------------------------- Variables statiques

//------------------------------------------------------ Fonctions privées


//////////////////////////////////////////////////////////////////  PUBLIC
//---------------------------------------------------- Fonctions publiques

void test()
{
	logstream logReader;
	logReader.open("anonyme.log");

	Graph instGraph;

	int i = 0;
	while (i<20)
	{
		i++;
		logElement tempLog = *logReader.getLog();
		instGraph.addTop10(tempLog.referer, tempLog.queryHit);
		instGraph.addGraph(tempLog.referer, tempLog.queryHit);
	}
	instGraph.createTop10();
	instGraph.createDotFile("Fichier.dot");
	instGraph.afficherMap();
}


int main(int argc, char *argv[])

{
	//cout << "nombre d'arguments " << argc << endl;
	// -------------------------- Initialisations
	string logFile;//string pour récupérer le nom du fichier
	logstream logReader;//fichier en entrée
	Graph instGraph;//objet graph
	// A priori on ne demande aucune option
	bool g = false;
	bool e = false;
	bool t = false;
	string heure;//heure si choix de l'option
	string dotFile;//nom du fichier dotfile si choix de l'option

	if (argc < 2 || argc >7) // Si le nombre d'arguments est faux-->commande focement fausse
	{
		cerr << "Not enough argument" << endl;
		return 1;
	}
	else // On traite la commande
	{
		logFile = argv[argc - 1];
		if (logFile.find_last_of(".log") != (logFile.length() - 1) && logFile.find_last_of(".txt") != (logFile.length() - 1)) {
			cerr << "The log file specified must be a .log or a .txt file" << endl;
			return 1;
		}
		logReader.open(logFile);
	}

	if (logReader) // Nous sommes arrivés à ouvrir le fichier
	{

	// -------------------------- Traitement de la commande

	

	for (int i = 1; i < argc - 1; ++i)
	{
		string temp = argv[i];
		if (temp == "-g")
		{
			
			g = true;
			if (argc > i + 2) // si il y'a un nom de fichier dot suivi par au moins un argument
			{
				dotFile = argv[++i];
				
				if (dotFile.find_last_of(".dot")!= (dotFile.length()-1)) {
					cerr << "The dotfile specified must be a .dot" << endl;
					return 1;
				}
			}
			else
			{
				cerr << "Dot File unspecified" << endl;
				return 1;
			}
		}
		else if (temp == "-e")
		{
			e = true;
			cout << "Warning : images, css and javascript files have been ignored." << endl;
		}
		else if (temp == "-t")
		{
			t = true;
			if (argc > i + 2)   // il y'a une heure suivie par au moins un argument
			{
				heure = argv[++i];
				if (heure.length()==1 && int( heure.at(0))>=48 && int(heure.at(0))<=57) {
					int heureToInt = stoi(heure);
					cout << " Warning : only hits between " << heureToInt << "h and " << heureToInt + 1 << "h have been taken into account." << endl;
				}
				else if (heure.length() == 2 && int(heure.at(0)) >= 48 && int(heure.at(0)) <= 57 && int(heure.at(1)) >= 48 && int(heure.at(1)) <= 57) {
					int heureToInt = stoi(heure);
					if (heureToInt>23 || heureToInt <0) {
						cerr << "An hour must be between 0 and 23." << endl;
						return 1;
					}
					cout << " Warning : only hits between " << heureToInt << "h and " << heureToInt + 1 << "h have been taken into account." << endl;
				}
				else {
					cout << "Invalid hour format." << endl;
				}
			}
			else
			{
				cerr << "The hour was not specified after the -t option." << endl;
				return 1;
			}
		}
		else {
			cerr << "Bad option" << endl;
			return 1;
		}
	}


	// -------------------------- Remplissage du graphe

	if (g) {
		cout << "Dot-file " << dotFile << " generated." << endl;
	}
		while (!logReader.eof())
		{
			
			logElement * tempLog = logReader.getLog(e,t,heure);   // !!!!! Problème de pointeur !
			if (tempLog != nullptr && tempLog->queryType == "GET" && tempLog->status.at(0) == '2')
			{
				
				instGraph.addTop10(tempLog->referer.substr(0, tempLog->referer.find_first_of('?')), tempLog->queryHit.substr(0, tempLog->queryHit.find_first_of('?')));
				if (g) { // On ne remplie la structure du graphe que si on a choisi l'option -g
					
					instGraph.addGraph(tempLog->referer.substr(0, tempLog->referer.find_first_of('?')), tempLog->queryHit.substr(0, tempLog->queryHit.find_first_of('?')));
				}
			}
		}
	}
	else 
	{
		cerr << "An error occured while trying to read your file, please make sure your file exists and is accessible." << endl;
		return 1;
	}


	// -------------------------- Executions
	instGraph.createTop10();
	instGraph.afficherTop10();

	if (g)
	{
		instGraph.createDotFile(dotFile);
	}
	
	return 0;
} //----- fin de Nom
