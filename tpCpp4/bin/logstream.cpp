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
#include <sstream>
#include <vector>

//------------------------------------------------------ Include personnel
#include "logstream.h"

//------------------------------------------------------------- Constantes
//----------------------------------------------------------------- PUBLIC

//----------------------------------------------------- Méthodes publiques
logElement * logstream::getLog(bool e, bool t, string heure)
// Algorithme :
//
{
	string s;
	logElement * newLog;
	string localhost = "http://intranet-if.insa-lyon.fr";
	if (std::getline(*this, s))
	{
		newLog = new logElement;								
		stringstream sLine(s);
		std::getline(sLine, newLog->IP, ' ');
		std::getline(sLine, newLog->userLog, ' ');
		std::getline(sLine, newLog->authUser, ' ');
		sLine.get();
		std::getline(sLine, newLog->date, ']');
		sLine.get();
		sLine.get();
		std::getline(sLine, newLog->queryType, ' ');
		std::getline(sLine, newLog->queryHit, ' ');
		std::getline(sLine, newLog->queryVersion, '\"');
		sLine.get();
		std::getline(sLine, newLog->status, ' ');
		std::getline(sLine, newLog->size, ' ');
		sLine.get();
		std::getline(sLine, newLog->referer, '\"');
		if (newLog->referer.find(localhost) != string::npos)
		{
			newLog->referer = newLog->referer.substr(localhost.length());
		}
		sLine.get();
		sLine.get();
		std::getline(sLine, newLog->client, '\"');
		sLine.get();
		if (e && isImage(newLog->queryHit))
		{
			newLog = nullptr;
		}

		else if (t && !isOnTime(newLog->date, heure))
		{
			newLog = nullptr;
		}
	}
	else
	{
		newLog = nullptr;
	}
	return newLog;



} //----- Fin de Méthode getLog
  //-------------------------------------------- Constructeurs - destructeur
logstream::logstream()
{
#ifdef MAP
	cout << "Appel au constructeur de <logstream>" << endl;
#endif
	ifstream();
	imageExt.push_back("js");
	imageExt.push_back("css");
	imageExt.push_back("png");
	imageExt.push_back("jpg");
	imageExt.push_back("gif");
	imageExt.push_back("svg");
	imageExt.push_back("ico");
}
//------------------------------------------------------------------ PRIVE

//----------------------------------------------------- Méthodes protégées

//Algorithme:
//Compare l'extension du hit avec les extensions contenues dans imageExt.
//Retourne vraie si une correspondance a été trouvée.
//Retourne faux sinon.
bool logstream::isImage(string hit)
{
	string ext;
	int pos = hit.find_last_of('.');
	if (pos>=0) 
	{
		 ext = hit.substr(pos+1); //ce qu'il ya apres le dernier point donc lextension
	}
	return (find(imageExt.begin(), imageExt.end(), ext) != imageExt.end()); 
}

//Algorithme:
//Compare l'heure de la date donnée en premier paramètre est égale
//à l'heure donnée en second paramètre.
//Retourne faux sinon.
bool logstream::isOnTime(string date, string heure)
{
	string time = date.substr(date.find_first_of(':') + 1, 2);	
	return !time.compare(heure);
}
