/*************************************************************************
Manager  -  description
-------------------
d�but                : 09/01/2018
copyright            : (C) 2018 par B3421
e-mail               : safia.el-bayed@insa-lyon.fr
gregoire.gentil@insa-lyon.fr
*************************************************************************/

//---------- R�alisation du module <Manager> (fichier Manager.cpp) ---------------

/////////////////////////////////////////////////////////////////  INCLUDE
//-------------------------------------------------------- Include syst�me
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

//------------------------------------------------------ Fonctions priv�es


//////////////////////////////////////////////////////////////////  PUBLIC
//---------------------------------------------------- Fonctions publiques
int main(int argc, char *argv[])

{
	// -------------------------- Initialisations
	string logFile;//string pour r�cup�rer le nom du fichier
	logstream logReader;//fichier en entr�e
	Graph instGraph;//objet graph
	// A priori on ne demande aucune option
	bool g = false;//option de création du graphe
	bool e = false;//option de l'élimination des images
	bool t = false;//option de filtrage en fonction de l'heure
	int heureToInt;//conversion de l'heure en entier si choix de l'option
	string heure;//heure si choix de l'option
	string dotFile;//nom du fichier dotfile si choix de l'option
	if (argc < 2 || argc >7) // Si le nombre d'arguments est faux-->commande forcement fausse
	{
		cerr << "Incorrect number of arguments." << endl;
		return 3;
	}
	else // Si le nombre d'arguments est bon , on traite la commande
	{
		logFile = argv[argc - 1];
		if (logFile.length() >= 4 && logFile.substr(logFile.length() - 4).compare(".log")  && logFile.substr(logFile.length() - 4).compare(".txt"))
		{
			cerr << "The log file specified must be a .log or a .txt file." << endl;
			return 5;
		}
		logReader.open(logFile);//Le fichier est au bon format, donc on l'ouvre
	}

	if (logReader) // Nous sommes arriv�s � ouvrir le fichier
	{
	// -------------------------- Traitement de la commande
		for (int i = 1; i < argc - 1; ++i)
		{
			string temp = argv[i];
			if (temp == "-g" && !g)
			{
				g = true;
				if (argc > i + 2) // si il y'a un nom de fichier dot suivi par au moins un argument
				{
					dotFile = argv[++i];
					if (!(dotFile.find_first_of('/') == string::npos && dotFile.find_first_of('\\') == string::npos && dotFile.find_first_of(':') == string::npos && dotFile.find_first_of('?') == string::npos && dotFile.find_first_of('>') == string::npos && dotFile.find_first_of('<') == string::npos && dotFile.find_first_of('|') == string::npos && dotFile.find_first_of('"') == string::npos))
					{
						cerr << "Invalid character in the dot file name." << endl;
						return 2;
					}
					else if (dotFile.find_last_of(".dot")!= (dotFile.length()-1))
					{
						cerr << "The dotfile specified must be a .dot." << endl;
						return 6;
					}
				}
				else
				{
					cerr << "Dot File unspecified." << endl;
					return 10;
				}
			}
			else if (temp == "-e" && !e)
			{
				e = true;

			}
			else if (temp == "-t" && !t)
			{
				t = true;
				if (argc > i + 2)   // il y'a une heure suivie par au moins un argument
				{
					heure = argv[++i];
					if (heure.length()==1 && int( heure.at(0))>=48 && int(heure.at(0))<=57) // heure composée d'un seul chiffre
					{
						 heureToInt= stoi(heure);
					}
					else if (heure.length() == 2 && int(heure.at(0)) >= 48 && int(heure.at(0)) <= 57 && int(heure.at(1)) >= 48 && int(heure.at(1)) <= 57)
					//Heure composée de deux nombres
					{
						heureToInt = stoi(heure);
						if (heureToInt>23 || heureToInt <0)
						{
							cerr << "An hour must be between 0 and 23." << endl;
							return 7;
						}
					}
					else//Heure non entière
					{
						cerr << "Invalid hour format." << endl;
						return 8;
					}
				}
				else
				{
					cerr << "The hour was not specified after the -t option." << endl;
					return 11;
				}
			}
			else
			{
				cerr << "Incorrect option." << endl;
				return 4;
			}
		}
		// -------------------------- Remplissage des structures de données
		if(t)
		{
			cout << " Warning : only hits between " << heureToInt << "h and " << heureToInt + 1 << "h have been taken into account." << endl;
		}
		if (e)
		{
			cout << "Warning : images, css and javascript files have been ignored." << endl;
		}
		while (!logReader.eof()) // Tant que la fin du fichier n'est pas atteinte
		{
			logElement * tempLog = logReader.getLog(e,t,heure);   // ligne décomposée du fichier log
			if (tempLog != nullptr && tempLog->queryType == "GET" /*&& tempLog->status.at(0) == '2'*/) // Possibilité de filtrer selon les status
			{
				//On remplie la structure du top 10.
				instGraph.addTop10(tempLog->referer.substr(0, tempLog->referer.find_first_of('?')), tempLog->queryHit.substr(0, tempLog->queryHit.find_first_of('?')));
				if (g)// On ne remplie la structure du graphe que si on a choisi l'option -g.
				{ 
					instGraph.addGraph(tempLog->referer.substr(0, tempLog->referer.find_first_of('?')), tempLog->queryHit.substr(0, tempLog->queryHit.find_first_of('?')));
				}
			}
		}
	}	// end if (logReader)
	else
	{
		cerr << "An error occured while trying to read your file, please make sure your file exists and is accessible." << endl;
		return 9;
	}
	// -------------------------- Executions
	instGraph.createTop10();
	instGraph.afficherTop10();
	if (g)
	{
		if (instGraph.createDotFile(dotFile))//Création réussie
		{
			cout << "Dot-file " << dotFile << " generated." << endl;
		}
		else//Erreur à la création
		{
			cerr << "Error during dot file creation." << endl;
			return 12;
		}
	}
	return 0;
} //----- fin de main
