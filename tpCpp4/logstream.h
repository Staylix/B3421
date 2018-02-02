/*************************************************************************
logstream  -  description
-------------------
d�but                : 09/01/2018
copyright            : (C) 2018 par B3421
e-mail               : safia.el-bayed@insa-lyon.fr
gregoire.gentil@insa-lyon.fr
*************************************************************************/

//---------- Interface de la classe <logstream> (fichier logstream.h) ----------------
#if ! defined ( logstream_H )
#define logstream_H

//--------------------------------------------------- Interfaces utilis�es
using namespace std;
#include <fstream>
#include <string>
#include <vector>
#include <algorithm>
//------------------------------------------------------------- Constantes

//------------------------------------------------------------------ Types

/*Structure servant � la d�composition d'une ligne du fichier journal de
fa�on � pouvoir �tre r�utilis�e par la suite.*/


//------------------------------------------------------------------------
// R�le de la classe <logstream>
//  La classe logstream h�rite de la classe ifstream
//  Elle permet de g�rer le fichier .log pass� en param�tre de la commande
//  de fa�on propre � la structure d'un fichier journal
//  Cette sp�cificit� lui permet d'�tre r�utilisable pour manipuler
//  ces fichiers dans d'autres applications
//------------------------------------------------------------------------

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

class logstream : public ifstream
{

	//----------------------------------------------------------------- PUBLIC

public:
	//----------------------------------------------------- M�thodes publiques
	logElement * getLog(bool e = false, bool t = false, string heure = "");
	// Mode d'emploi :
	// Cette m�thode permet de lire le fichier log ligne par ligne, et de
	// mettre son contenu dans un objet log, structure contenant un
	// ensemble de chaines de caract�res qui correspondent aux
	// donn�es contenues dans le fichier journal (adresseIP, userLog,...).
	// Contrat :
	// Aucun.

	bool isImage(string hit);
	bool isOnTime(string date, string heure);

	//------------------------------------------------- Surcharge d'op�rateurs


	//-------------------------------------------- Constructeurs - destructeur

	logstream();

	//------------------------------------------------------------------ PRIVE

protected:
	//----------------------------------------------------- M�thodes prot�g�es

	//----------------------------------------------------- Attributs prot�g�s

	vector<string> imageExt;

};



//-------------------------------- Autres d�finitions d�pendantes de <logstream>

#endif // logstream_H
