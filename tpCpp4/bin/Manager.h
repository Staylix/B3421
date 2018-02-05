/*************************************************************************
Manager  -  description
-------------------
début                : 09/01/2018
copyright            : (C) 2018 par B3421
e-mail               : safia.el-bayed@insa-lyon.fr
gregoire.gentil@insa-lyon.fr
*************************************************************************/

//---------- Interface du module <Manager> (fichier Manager.h) -----------
#if ! defined ( Manager_H )
#define Manager_H

//------------------------------------------------------------------------
// Rôle du module <Manager>
//  Ce module se charge de lancer l'application. Il contient la fonction
//  main, point d'entrée du programme. Il gère le traitement des commandes
//	lancées par l'utilisateur et les cas limites du fonctionnement.
//------------------------------------------------------------------------

/////////////////////////////////////////////////////////////////  INCLUDE
//--------------------------------------------------- Interfaces utilisées
using namespace std;

//////////////////////////////////////////////////////////////////  PUBLIC
//---------------------------------------------------- Fonctions publiques
int main(int argc, char *argv[]);
// Mode d'emploi :
//  Lance l'application.
// Contrat :
//  Aucun.

#endif // Manager_H
