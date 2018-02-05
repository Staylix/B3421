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
// R�le de la classe <logstream>
//  La classe logstream h�rite de la classe ifstream
//  Elle permet de g�rer le fichier .log pass� en param�tre de la commande
//  de fa�on propre � la structure d'un fichier journal.
//  Cette sp�cificit� lui permet d'�tre r�utilisable pour manipuler
//  ces fichiers dans d'autres applications.
//------------------------------------------------------------------------

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
	// Elle peut filtrer les r�sultats selon les options choisie par l'utilisateur.
	// Si la ligne lue ne correspond pas aux filtres, la m�thode retourne un
	// nullptr.
	// Contrat :
	// Aucun.

	bool isImage(string hit);
	// Mode d'emploi :
	// Cette m�thode permet de dire si l'extension du nom de document
	// fourni en param�tre correspond � une des extensions contenues dans
	// le tableau de chaines de carac�tres imageExt (attribut de la classe
	// logstream).
	// Retourne vrai si c'est bien le cas, faux sinon.
	// Contrat :
	// Aucun.
	
	bool isOnTime(string date, string heure);
	// Mode d'emploi :
	// Cette m�thode permet de dire si l'heure de la date donn�e en premier
	// param�tre est bien �gale � l'heure donn�e en deuxi�me param�tre.
	// Retourne vrai si c'est bien le cas, faux sinon.
	// Contrat :
	// Aucun.

	//------------------------------------------------- Surcharge d'op�rateurs


	//-------------------------------------------- Constructeurs - destructeur

	logstream();
	// Mode d'emploi :
	// Le constructeur fait appel au constructeur de la classe ifstream.
	// Puis, il initialise le tableau de chaines de caract�res imageExt 
	// avec les extensions � �liminer lorsque l'option -e est s�lectionn�e.
	// Contrat :
	// Aucun.

	//------------------------------------------------------------------ PRIVE

protected:
	//----------------------------------------------------- M�thodes prot�g�es
	//----------------------------------------------------- Attributs prot�g�s

	vector<string> imageExt;
	// Le tableau de chaines de caract�res imageExt contenant les extensions 
	// � �liminer lorsque l'option -e est s�lectionn�e.

};



//-------------------------------- Autres d�finitions d�pendantes de <logstream>

#endif // logstream_H
