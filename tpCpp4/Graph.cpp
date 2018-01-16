/*************************************************************************
                           Graph  -  description
                             -------------------
     début                : 09/01/2018
     copyright            : (C) 2018 par B3421
     e-mail               : safia.el-bayed@insa-lyon.fr
                            gregoire.gentil@insa-lyon.fr
*************************************************************************/

//---------- Réalisation de la classe <Graph> (fichier Graph.cpp) ------------

//---------------------------------------------------------------- INCLUDE

//-------------------------------------------------------- Include système
using namespace std;
#include <iostream>

//------------------------------------------------------ Include personnel
#include "Graph.h"

//------------------------------------------------------------- Constantes

//----------------------------------------------------------------- PUBLIC

//----------------------------------------------------- Méthodes publiques
// type Graph::Méthode ( liste des paramètres )
// Algorithme :
//
//{
//} //----- Fin de Méthode

void Graph::afficherMap()
{
	cout<<"Voici la unordered_map graph : "<<endl;
	for(unordered_map<string,int>::iterator it=graph.begin();it!=graph.end();++it)
	{
		cout<<"( "<<it->first<<" , "<<it->second<<" ) "<<endl;
	}
	cout<<"Voici la unordered_map passage : "<endl;
	for(it=passage.begin();it!=passage.end();++it)
	{
		cout<<"( "<<it->first<<" , "<<it->second<<" ) "<<endl;
	}
	cout<<"Voici la multimap top10 : "<endl;
	for(multimap<int,string>::iterator ite=top10.begin();ite!=top10.end();++ite)
	{
		cout<<"( "<<ite->second<<" , "<<ite->first<<" ) "<<endl;
	}

}

void Graph::add(string ref, string hit)
{
 pair<unordered_map<string,int>::iterator,bool>  retour =graph.insert(make_pair(ref+" "+hit,1); 
 if(!retour.second)
 {
 graph[ref+" "+hit]=graph[ref+" "+hit]+1;
 }
 retour =passage.insert(make_pair(hit,1); 
 if(!retour.second)
 {
 passage[hit]=passage[hit]+1;
 }                                                                                                                                                                                         
}


void Graph::createTop10()
{
	for(unordered_map<string,int>::iterator it=passage.begin(); it != passage.end(); ++it)
	{
		pair<multimap<int,string>::iterator,bool>  retour =top10.insert(make_pair(it->second,it->first); 
	}
}
  

void Graph::createDotFile(string nomFichier)
{
	ofstream dot;
	dot.open(nomFichier,std::ofstream::out | std::ofstream::trunc);
	int i=0;
	dot.write("digraph {\n");
	for(unordered_map<string,int>::iterator it=passage.begin(); it != passage.end(); ++it)
	{
		dot.write("node"+i+" [label=\""+it->first+"\"];\n");
		registry.insert(make_pair(it->first,i));
		++i; 
	}
	for(unordered_map<string,int>::iterator it=graph.begin(); it != graph.end(); ++it)
	{
		stringstream  s(it->first);
		string hit;
		string referer;
		getline(s,referer," ");
		getline(s,hit," ");
		int indexRef=registry[referer];
		int indexHit=registry[hit];
		string aecrire="node"+indexRef+" -> node"+indexHit+" [label=\""+it->second+"\"];\n";
		dot.write(aecrire);
	}
	dot.write("}");
}

void Graph::afficherTop10()
{
	int compteur=0;
	for(multimap<int,string>::iterator it=top10.rbegin(); it != top10.rend() || compteur==10; --it,++compteur)
	{
		cout<<it->second<<" ("<<it->first<<" hits)"<<endl;
	}
	
}

//-------------------------------------------- Constructeurs - destructeur

Graph::Graph ( )
// Algorithme :
//
{
#ifdef MAP
    cout << "Appel au constructeur de <Graph>" << endl;
#endif
} //----- Fin de Graph


Graph::~Graph ( )
// Algorithme :
//
{
#ifdef MAP
    cout << "Appel au destructeur de <Graph>" << endl;
#endif
} //----- Fin de ~Graph


//------------------------------------------------------------------ PRIVE

//----------------------------------------------------- Méthodes protégées
