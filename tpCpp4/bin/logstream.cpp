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
	//string s = "192.168.0.0 - - [08/Sep/2012:11:16:02 +0200] \"GET /temps/4IF16.html HTTP/1.1\" 200 12106 \"http://intranet-if.insa-lyon.fr/temps/4IF15.html\" \"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:14.0) Gecko/20100101 Firefox/14.0.1\""
	if (std::getline(*this, s))
	{
		newLog = new logElement;								// cout à supprimer !!!!!!!!!
		stringstream sLine(s);
		std::getline(sLine, newLog->IP, ' ');
		//cout << newLog->IP << endl;
		std::getline(sLine, newLog->userLog, ' ');
		//cout << newLog->userLog << endl;
		std::getline(sLine, newLog->authUser, ' ');
		//cout << newLog->authUser << endl;
		sLine.get();
		std::getline(sLine, newLog->date, ']');
		//cout << newLog->date << endl;
		sLine.get();
		sLine.get();
		std::getline(sLine, newLog->queryType, ' ');
		//cout << newLog->queryType << endl;
		std::getline(sLine, newLog->queryHit, ' ');
		//cout << newLog->queryHit << endl;
		std::getline(sLine, newLog->queryVersion, '\"');
		//cout << newLog->queryVersion << endl;
		sLine.get();
		std::getline(sLine, newLog->status, ' ');
		//cout << newLog->status << endl;
		std::getline(sLine, newLog->size, ' ');
		//cout << newLog->size << endl;
		sLine.get();
		std::getline(sLine, newLog->referer, '\"');
		if (newLog->referer.find(localhost) != string::npos)
		{
			newLog->referer = newLog->referer.substr(localhost.length());
		}
		//cout << newLog->referer << endl;
		sLine.get();
		sLine.get();
		std::getline(sLine, newLog->client, '\"');
		//cout << newLog->client << endl;
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

  //------------------------------------------------- Surcharge d'opérateurs
  //-------------------------------------------- Constructeurs - destructeur
logstream::logstream()
{
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

bool logstream::isImage(string hit)
{
	string ext;
	int pos = hit.find_last_of('.');
	if (pos>=0) 
	{
		 ext = hit.substr(pos+1); //ce qu'il ya apres le dernier point donc lextension
	}
	return (find(imageExt.begin(), imageExt.end(), ext) != imageExt.end()); //est ce qu'on la trouve ou on est arrive au end 
}

bool logstream::isOnTime(string date, string heure)
{
	string time = date.substr(date.find_first_of(':') + 1, 2);	
	return !time.compare(heure);
}
