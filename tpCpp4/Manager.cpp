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


int main(int argc, char *argv[])
// Algorithme :
//
{
    logstream logReader;
    logReader.open("anonyme.log");

    Graph g;

    int i = 0;
    while (i<20)
    {
        i++;
        log tempLog = *logReader.getLog();
        g.add(tempLog.referer, tempLog.queryHit);
    }
    g.createTop10();
    g.createDotFile("Fichier.dot");
    g.afficherMap();

} //----- fin de Nom
