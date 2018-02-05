/*************************************************************************
Manager  -  description
-------------------
d�but                : 09/01/2018
copyright            : (C) 2018 par B3421
e-mail               : safia.el-bayed@insa-lyon.fr
gregoire.gentil@insa-lyon.fr
*************************************************************************/

//---------- Interface du module <Manager> (fichier Manager.h) -----------
#if ! defined ( Manager_H )
#define Manager_H

//------------------------------------------------------------------------
// R�le du module <Manager>
//  Ce module se charge de lancer l'application. Il contient la fonction
//  main, point d'entr�e du programme. Il g�re le traitement des commandes
//	lanc�es par l'utilisateur et les cas limites du fonctionnement.
//------------------------------------------------------------------------

/////////////////////////////////////////////////////////////////  INCLUDE
//--------------------------------------------------- Interfaces utilis�es
using namespace std;

//////////////////////////////////////////////////////////////////  PUBLIC
//---------------------------------------------------- Fonctions publiques
int main(int argc, char *argv[]);
// Mode d'emploi :
//  Lance l'application.
// Contrat :
//  Aucun.

#endif // Manager_H
