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
        log tempLog = *logReader.getLog();
        instGraph.add(tempLog.referer, tempLog.queryHit);
    }
    instGraph.createTop10();
    instGraph.createDotFile("Fichier.dot");
    instGraph.afficherMap();
}


int main(int argc, char *argv[])
// Algorithme :
//
{
    test();

// -------------------------- Initialisations
    string logFile;
    logstream logReader;
    Graph instGraph;

    if (argc >= 2)
    {
        logFile = argv[argc-1];
        logReader.open(logFile);
    }
    else
    {
        cerr << "No enough argument" << endl;
    }


// -------------------------- Traitement de la commande

    bool g = false;
    bool e = false;
    bool t = false;
    string heure;
    string dotFile;

    for (int i = 1; i < argc-1; ++i)
    {
        string temp = argv[i];
        if (temp == "-g")
        {
            g = true;
            if (argc >= i+2)
            {
                dotFile = argv[++i];
            }
            else
            {
                cerr << "Dot File unspecified" << endl;
            }
        }
        else if (temp == "-e")
        {
            e = true;
        }
        else if (temp == "-t")
        {
            t = true;
            if (argc >= i+2)   // !!!! Format de l'heure pour les comparaisons ?
            {
                heure = argv[++i];
            }
            else
            {
                cerr << "Time unspecified" << endl;
            }
        }
    }


// -------------------------- Remplissage du graphe

    if (logReader)
    {
        while (!logReader.eof())
        {
            log tempLog = *logReader.getLog();   // !!!!! Problème de pointeur !
            instGraph.add(tempLog.referer, tempLog.queryHit);    // Ajouter les paramètres t et e !!!!!
        }
    }
    else
    {
        cerr << "Error with file" << endl;
    }


// -------------------------- Executions
    instGraph.createTop10();

    if (g)
    {
        instGraph.createDotFile(dotFile);
    }


} //----- fin de Nom
