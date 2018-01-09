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

//------------------------------------------------------------- Constantes

//------------------------------------------------------------------ Types

//------------------------------------------------------------------------
// Rôle de la classe <logstream>
//  La classe logstream hérite de la classe ifstream
//  Elle permet de gérer le fichier .log passé en paramètre de la commande
//  de façon propre à la structure d'un fichier journal
//  Cette spécificité lui permet d'être réutilisable pour manipuler
//  ces fichiers dans d'autres applications
//------------------------------------------------------------------------

class logstream : public Ancetre
{
//----------------------------------------------------------------- PUBLIC

public:
//----------------------------------------------------- Méthodes publiques
    // type Méthode ( liste des paramètres );
    // Mode d'emploi :
    //
    // Contrat :
    //


//------------------------------------------------- Surcharge d'opérateurs
    logstream & operator = ( const logstream & unlogstream );
    // Mode d'emploi :
    //
    // Contrat :
    //


//-------------------------------------------- Constructeurs - destructeur
    logstream ( const logstream & unlogstream );
    // Mode d'emploi (constructeur de copie) :
    //
    // Contrat :
    //

    logstream ( );
    // Mode d'emploi :
    //
    // Contrat :
    //

    virtual ~logstream ( );
    // Mode d'emploi :
    //
    // Contrat :
    //

//------------------------------------------------------------------ PRIVE

protected:
//----------------------------------------------------- Méthodes protégées

//----------------------------------------------------- Attributs protégés

};

//-------------------------------- Autres définitions dépendantes de <logstream>

#endif // logstream_H
