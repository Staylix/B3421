/*************************************************************************
logstream  -  description
-------------------
début                : 09/01/2018
copyright            : (C) 2018 par B3421
e-mail               : safia.el-bayed@insa-lyon.fr
gregoire.gentil@insa-lyon.fr
*************************************************************************/

//---------- Interface de la classe <logstream> (fichier logstream.h) ----------------
#if ! defined ( logstream_H )
#define logstream_H

//--------------------------------------------------- Interfaces utilisées
using namespace std;
#include <fstream>
#include <string>
#include <vector>
#include <algorithm>
//------------------------------------------------------------- Constantes

//------------------------------------------------------------------ Types

/*Structure servant à la décomposition d'une ligne du fichier journal de
façon à pouvoir être réutilisée par la suite.*/
typedef struct {
	string IP;
	string userLog;
	string authUser;
	string date;
	string queryType;
	string queryHit;
	string queryVersion;
	string status;
	string size;
	string referer;
	string client;
} logElement;

//------------------------------------------------------------------------
// Rôle de la classe <logstream>
//  La classe logstream hérite de la classe ifstream
//  Elle permet de gérer le fichier .log passé en paramètre de la commande
//  de façon propre à la structure d'un fichier journal.
//  Cette spécificité lui permet d'être réutilisable pour manipuler
//  ces fichiers dans d'autres applications.
//------------------------------------------------------------------------

class logstream : public ifstream
{

	//----------------------------------------------------------------- PUBLIC

public:
	//----------------------------------------------------- Méthodes publiques
	logElement * getLog(bool e = false, bool t = false, string heure = "");
	// Mode d'emploi :
	// Cette méthode permet de lire le fichier log ligne par ligne, et de
	// mettre son contenu dans un objet log, structure contenant un
	// ensemble de chaines de caractères qui correspondent aux
	// données contenues dans le fichier journal (adresseIP, userLog,...).
	// Elle peut filtrer les résultats selon les options choisie par l'utilisateur.
	// Si la ligne lue ne correspond pas aux filtres, la méthode retourne un
	// nullptr.
	// Contrat :
	// Aucun.

	bool isImage(string hit);
	// Mode d'emploi :
	// Cette méthode permet de dire si l'extension du nom de document
	// fourni en paramètre correspond à une des extensions contenues dans
	// le tableau de chaines de caracètres imageExt (attribut de la classe
	// logstream).
	// Retourne vrai si c'est bien le cas, faux sinon.
	// Contrat :
	// Aucun.
	
	bool isOnTime(string date, string heure);
	// Mode d'emploi :
	// Cette méthode permet de dire si l'heure de la date donnée en premier
	// paramètre est bien égale à l'heure donnée en deuxième paramètre.
	// Retourne vrai si c'est bien le cas, faux sinon.
	// Contrat :
	// Aucun.

	//------------------------------------------------- Surcharge d'opérateurs


	//-------------------------------------------- Constructeurs - destructeur

	logstream();
	// Mode d'emploi :
	// Le constructeur fait appel au constructeur de la classe ifstream.
	// Puis, il initialise le tableau de chaines de caractères imageExt 
	// avec les extensions à éliminer lorsque l'option -e est sélectionnée.
	// Contrat :
	// Aucun.

	//------------------------------------------------------------------ PRIVE

protected:
	//----------------------------------------------------- Méthodes protégées
	//----------------------------------------------------- Attributs protégés

	vector<string> imageExt;
	// Le tableau de chaines de caractères imageExt contenant les extensions 
	// à éliminer lorsque l'option -e est sélectionnée.

};



//-------------------------------- Autres définitions dépendantes de <logstream>

#endif // logstream_H
