/*************************************************************************
                           logstream  -  description
                             -------------------
     début                : 09/01/2018
     copyright            : (C) 2018 par B3421
     e-mail               : safia.el-bayed@insa-lyon.fr
                            gregoire.gentil@insa-lyon.fr
*************************************************************************/

//---------- Réalisation de la classe <logstream> (fichier logstream.cpp) ------------

//---------------------------------------------------------------- INCLUDE

//-------------------------------------------------------- Include système
using namespace std;
#include <iostream>

//------------------------------------------------------ Include personnel
#include "logstream.h"

//------------------------------------------------------------- Constantes

//----------------------------------------------------------------- PUBLIC

//----------------------------------------------------- Méthodes publiques
// type logstream::Méthode ( liste des paramètres )
// Algorithme :
//
//{
//} //----- Fin de Méthode


//------------------------------------------------- Surcharge d'opérateurs
logstream & logstream::operator = ( const logstream & unlogstream )
// Algorithme :
//
{
} //----- Fin de operator =


//-------------------------------------------- Constructeurs - destructeur
logstream::logstream ( const logstream & unlogstream )
// Algorithme :
//
{
#ifdef MAP
    cout << "Appel au constructeur de copie de <logstream>" << endl;
#endif
} //----- Fin de logstream (constructeur de copie)


logstream::logstream ( )
// Algorithme :
//
{
#ifdef MAP
    cout << "Appel au constructeur de <logstream>" << endl;
#endif
} //----- Fin de logstream


logstream::~logstream ( )
// Algorithme :
//
{
#ifdef MAP
    cout << "Appel au destructeur de <logstream>" << endl;
#endif
} //----- Fin de ~logstream


//------------------------------------------------------------------ PRIVE

//----------------------------------------------------- Méthodes protégées
