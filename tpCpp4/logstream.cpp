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

//------------------------------------------------------ Include personnel
#include "logstream.h"

//------------------------------------------------------------- Constantes

//----------------------------------------------------------------- PUBLIC

//----------------------------------------------------- Méthodes publiques
log * logstream::getLog (bool e, bool t, string heure)
// Algorithme :
//
{
    string s;
    log* newLog;
    //string s = "192.168.0.0 - - [08/Sep/2012:11:16:02 +0200] \"GET /temps/4IF16.html HTTP/1.1\" 200 12106 \"http://intranet-if.insa-lyon.fr/temps/4IF15.html\" \"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:14.0) Gecko/20100101 Firefox/14.0.1\""
    if (std::getline(*this, s))
    {
        newLog = new log;
        stringstream sLine(s);
        std::getline(sLine, newLog->IP, ' ');
        cout << newLog->IP << endl;
        std::getline(sLine, newLog->userLog, ' ');
        cout << newLog->userLog << endl;
        std::getline(sLine, newLog->authUser, ' ');
        cout << newLog->authUser << endl;
        std::getline(sLine, newLog->date, ' ');
        cout << newLog->date << endl;
        sLine.get();
        std::getline(sLine, newLog->queryType, ' ');
        cout << newLog->queryType << endl;
        std::getline(sLine, newLog->queryHit, ' ');
        cout << newLog->queryHit << endl;
        std::getline(sLine, newLog->queryVersion, '\"');
        cout << newLog->queryVersion << endl;
        sLine.get();
        std::getline(sLine, newLog->status, ' ');
        cout << newLog->status << endl;
        std::getline(sLine, newLog->size, ' ');
        cout << newLog->size << endl;
        sLine.get();
        std::getline(sLine, newLog->referer, '\"');
        cout << newLog->referer << endl;
        sLine.get();
        sLine.get();
        std::getline(sLine, newLog->client, '\"');
        cout << newLog->client << endl;
        sLine.get();
    }
    else
    {
        newLog = nullptr;
    }

    return newLog;



} //----- Fin de Méthode getLog

//------------------------------------------------- Surcharge d'opérateurs
//-------------------------------------------- Constructeurs - destructeur
//------------------------------------------------------------------ PRIVE

//----------------------------------------------------- Méthodes protégées
