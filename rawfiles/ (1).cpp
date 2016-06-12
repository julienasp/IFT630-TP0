/**
 * \file Graphe.cpp
 * \brief Implémentation d'un graphe orienté où chaque sommet un nom.
 * \author ...
 * \version 0.1
 * \date ...
 *
 *  Travail pratique numéro 3
 *
 */

#ifdef __unix__ 
# include <unistd.h>
#elif defined _WIN32 
# include <windows.h>
#endif

#define PI 3.14159
#include "Graphe.h"

//vous pouvez inclure d'autres librairies si c'est nécessaire

namespace TP3
{
/*
 * \fn Graphe::Graphe()
 */
Graphe::Graphe()
{
	nbSommets = 0;
	nbArcs = 0;
	listeSommets = 0;
}
/*
 * \fn Graphe::Graphe(const Graphe &source)
 * \param[in] source un graphe à copier
 */
Graphe::Graphe(const Graphe &source)
{
	nbSommets = source.nbSommets;
	nbArcs = source.nbArcs;
	listeSommets = source.listeSommets;

	Sommet* courant = listeSommets;
	Sommet* nouveau = source.listeSommets;

	for (int i=0; i< nbSommets; i++)
	{
		courant = &*nouveau;
		Arc* arcCourant = courant->listeDest;
		Arc* arcNouveau = nouveau->listeDest;
		while (arcNouveau != 0)
		{
			arcCourant = &*arcNouveau;
			arcCourant = arcCourant->suivDest;
			arcNouveau = arcNouveau->suivDest;
		}
		courant = courant->suivant;
		nouveau = nouveau->suivant;
	}

}
/*
 * \fn Graphe::~Graphe()
 */
Graphe::~Graphe()
{
	detruireGraphe();
}
/*
 * \fn Graphe& Graphe::operator=(const Graphe& src)
 * \param[in] src un graphe à copier
 * \return Le graphe contient les infos du graphe src
 */
Graphe& Graphe::operator=(const Graphe& src)
{
	detruireGraphe();

	nbSommets = src.nbSommets;
		nbArcs = src.nbArcs;
		listeSommets = src.listeSommets;

		Sommet* courant = listeSommets;
		Sommet* nouveau = src.listeSommets;

		for (int i=0; i< nbSommets; i++)
		{
			courant = &*nouveau;
			Arc* arcCourant = courant->listeDest;
			Arc* arcNouveau = nouveau->listeDest;
			while (arcNouveau != 0)
			{
				arcCourant = &*arcNouveau;
				arcCourant = arcCourant->suivDest;
				arcNouveau = arcNouveau->suivDest;
			}
			courant = courant->suivant;
			nouveau = nouveau->suivant;
		}
	return (*this);
}
/*
 * \fn bool Graphe::arcExiste(const std::string& sommetUn, const std::string& sommetDeux) const
 * \param[in] sommetUn le premier sommet de l'arc
 * \param[in] sommetDeux le deuxieme sommet de l'arc
 * \return bool indiquant si l'arc existe
 */
bool Graphe::arcExiste(const std::string& sommetUn, const std::string& sommetDeux) const
{
	if (!(sommetExiste(sommetUn) && sommetExiste(sommetDeux))) throw std::logic_error("arcExiste: Un ou plusieurs somments manquants");
	Sommet * courant = listeSommets;

	//Les arcs n'ont pas de direction, il suffit donc juste de vérifier la liste des arcs à partir
	//d'un des deux sommets.
	for (int i=0; i<nbSommets; i++)
	{
		if (courant->nom == sommetUn)
		{
			Arc* arcCourant = courant->listeDest;
			while (arcCourant != 0)
			{
				if (arcCourant->dest->nom == sommetDeux) return true;
				arcCourant = arcCourant->suivDest;
			}

		}
		else courant = courant->suivant;
	}
	return false;
}
/*
 * \fn void Graphe::enleverArc(const std::string& nom1, const std::string& nom2)
 * \param[in] nom1 le nom du premier sommet de l'arc
 * \param[in] nom2 le nom du deuxieme sommet de l'arc
 */
void Graphe::enleverArc(const std::string& nom1, const std::string& nom2)
{
	if (!(arcExiste(nom1, nom2))) throw std::logic_error("enleverArc: L'arc n'existe pas.");

	Sommet* courant = listeSommets;
	Arc* aSupprimer = new Arc();
	for (int i=0; i<nbSommets; i++)
	{
		if (courant->nom == nom1)
		{
			//L'arc est le premier élément de la liste
			if (courant->listeDest->dest->nom == nom2)
			{
				//La liste contient plus d'un arc
				if (courant->listeDest->suivDest != 0)
				{
					aSupprimer = courant->listeDest;
					courant->listeDest = courant->listeDest->suivDest;
				}
				//La liste contient un seul arc
				else courant->listeDest = 0;
			}
			else
			{
				Arc* arcCourant = courant->listeDest;
				while(arcCourant->suivDest->dest->nom != nom2)
				{
					arcCourant = arcCourant->suivDest;
				}
				//L'arc est le dernier de la liste
				if (arcCourant->suivDest->suivDest == 0)
				{
					aSupprimer = arcCourant->suivDest;
					arcCourant->suivDest = 0;
				}
				else
				{
					aSupprimer = arcCourant->suivDest;
					arcCourant->suivDest = arcCourant->suivDest->suivDest;
				}
			}
		}
		else if (courant->nom == nom2)
		{
			//L'arc est le premier élément de la liste
			if (courant->listeDest->dest->nom == nom1)
			{
				//La liste contient plus d'un arc
				if (courant->listeDest->suivDest != 0)
				{
					aSupprimer = courant->listeDest;
					courant->listeDest = courant->listeDest->suivDest;
				}
				//La liste contient un seul arc
				else courant->listeDest = 0;
			}
			else
			{
				Arc* arcCourant = courant->listeDest;
				while(arcCourant->suivDest->dest->nom != nom1)
				{
					arcCourant = arcCourant->suivDest;
				}
				//L'arc est le dernier de la liste
				if (arcCourant->suivDest->suivDest == 0)
				{
					aSupprimer = arcCourant->suivDest;
					arcCourant->suivDest = 0;
				}
				else
				{
					aSupprimer = arcCourant->suivDest;
					arcCourant->suivDest = arcCourant->suivDest->suivDest;
				}
			}
		}
		courant = courant->suivant;
	}
	delete aSupprimer;
	nbArcs--;
}
/*
 * \fn void Graphe::ajouterArc(const std::string& nom1, const std::string& nom2, float duree, float cout, int ns)
 * \param[in] nom1 le nom du premier sommet de l'arc
 * \param[in] nom2 le nom u deuxieme sommet de l'arc
 * \param[in] duree la duree de l'arc
 * \param[in] cout le cout de l'arc
 * \param[in] ns le niveau de securite de l'arc
 */
void Graphe::ajouterArc(const std::string& nom1, const std::string& nom2, float duree, float cout, int ns)
{
	if (!(sommetExiste(nom1) && sommetExiste(nom2))) throw std::logic_error("ajouterArc: Un ou plusieurs somments manquants");
	if (arcExiste(nom1, nom2)) throw std::logic_error("ajouterArc: L'arc existe deja");

	//On récupère les adresses des sommets nom1 et nom2
	Sommet* courant = listeSommets;
	Sommet* un;
	Sommet* deux;
	for (int i=0; i < nbSommets; i++)
	{
		if (courant->nom == nom1)un = courant;
		else if (courant->nom  == nom2) deux = courant;
		courant = courant->suivant;
	}
	courant = listeSommets;
	Arc* nouveau = new Arc();
	nouveau->ponder.cout = cout;
	nouveau->ponder.duree = duree;
	nouveau->ponder.ns = ns;

	Arc* temp = new Arc();
	temp->ponder.cout = cout;
	temp->ponder.duree = duree;
	temp->ponder.ns = ns;
	for (int i=0; i < nbSommets; i++)
	{
		if (courant->nom == nom1)
		{
			if (courant->listeDest == 0)
			{
				nouveau->dest = deux;
				courant->listeDest = &*nouveau;
			}
			else
			{
				Arc* dest = courant->listeDest;
				while (dest->suivDest != 0) dest = dest->suivDest;
				nouveau->dest = deux;
				dest->suivDest = &*nouveau;
			}
		}
		else if (courant->nom  == nom2)
		{
			if (courant->listeDest == 0)
			{
				temp->dest = un;
				courant->listeDest = &*temp;
			}
			else
			{
				Arc* dest = courant->listeDest;
				while (dest->suivDest != 0) dest = dest->suivDest;
				temp->dest = un;
				dest->suivDest = &*temp;
			}
		}
		courant = courant->suivant;
	}
	nbArcs++;
}
/*
 * \fn void Graphe::ajouterSommet(const std::string& nom, float lt, float lg)
 * \param[in] nom le nom du sommet à ajouter
 * \param[in] lt la latitude du sommet
 * \param[in] lg la longitude du sommet
 */
void Graphe::ajouterSommet(const std::string& nom, float lt, float lg)
{
	if(sommetExiste(nom)) throw std::logic_error("AjouterSommet: le sommet existe deja!\n");

	Sommet* nouveau = new Sommet;
	nouveau->nom = nom;
	nouveau->coord.lg = lg;
	nouveau->coord.lt = lt;
	Sommet* courant = listeSommets;

	if (nbSommets == 0)
	{
		listeSommets = &*nouveau;
		listeSommets->precedent = 0;
	}
	else if (nbSommets == 1)
	{
		listeSommets->suivant = nouveau;
		nouveau->precedent = listeSommets;
	}
	else
	{
		for (int i=1; i<nbSommets; i++) courant = courant->suivant;
		courant->suivant = &*nouveau;
		courant->suivant->precedent = courant;
	}

	nbSommets++;
}
/*
 * \fn bool Graphe::sommetExiste(const std::string& nom) const
 * \param[in] nom le nom du sommet à valider
 * \return bool un booléen représentant la présence du sommet
 */
bool Graphe::sommetExiste(const std::string& nom) const
{
	if (nbSommets == 0) return false;
	Sommet* courant = listeSommets;
	for (int j = 0; j < nbSommets; j++)
	{
		if(nom == courant->nom) return true;
		courant = courant->suivant;
	}
	return false;
}
/*
 * \fn void Graphe::enleverSommet(const std::string& nom)
 * \param[in] nom le nom du sommet à enlever
 */
void Graphe::enleverSommet(const std::string& nom)
{
	if (!sommetExiste(nom)) throw std::logic_error("Le sommet n'existe pas");
	if (nbSommets == 1)
	{
		listeSommets = 0;
	}
	else
	{
		Sommet* courant = listeSommets;
		for (int i=0; i<nbSommets; i++)
		{
			if (courant->nom == nom)
			{
				//l'élément à delete est le premier de la liste
				if (courant->precedent == 0)
				{
					listeSommets = listeSommets->suivant;
					listeSommets->precedent = 0;
					Arc* arcCourant = courant->listeDest;
					Arc* arcTemp = arcCourant;
					while (arcTemp != 0)
					{
						arcTemp = arcTemp->suivDest;
						enleverArc(courant->nom, arcCourant->dest->nom);
						arcCourant = arcTemp;
					}
					delete courant;
					nbSommets--;
					return;
				}
				//l'élément à delete est le dernier de la liste
				else if (courant->suivant == 0)
				{
					Arc* arcCourant = courant->listeDest;
					Arc* arcTemp = arcCourant;
					while (arcTemp != 0)
					{
						arcTemp = arcTemp->suivDest;
						enleverArc(courant->nom, arcCourant->dest->nom);
						arcCourant = arcTemp;
					}
					courant->precedent->suivant = 0;
					delete courant;
					nbSommets--;
					return;
				}
				//l'élément à delete n'est pas le premier, ni le dernier
				else
				{
					Arc* arcCourant = courant->listeDest;
					Arc* arcTemp = arcCourant;
					while (arcTemp != 0)
					{
						arcTemp = arcTemp->suivDest;
						enleverArc(courant->nom, arcCourant->dest->nom);
						arcCourant = arcTemp;
					}
					courant->precedent->suivant = courant->suivant;
					courant->suivant->precedent = courant->precedent;
					delete courant;
					nbSommets--;
					return;
				}
			}
			courant = courant->suivant;
		}
	}
}
/*
 * \fn std::vector<std::string> Graphe::listerNomsSommets() const
 * \return un vector de string contenant tous les noms des sommets
 */
std::vector<std::string> Graphe::listerNomsSommets() const
{
	if (nbSommets == 0) throw std::logic_error("Aucun sommet présent dans le réseau");

	std::vector<std::string> sommets;
	Sommet* courant = listeSommets;
	for (int i=0; i< nbSommets; i++)
	{
		sommets.push_back(courant->nom);
		courant = courant->suivant;
	}
	return sommets;
}
/*
 * \fn std::vector<std::string> Graphe::listerSommetsAdjacents(const std::string& nom) const
 * \param[in] nom le nom du sommet duquel on veut trouver les sommets adjacents
 * \return un vector de string contenant tous les noms des sommets adjacents à nom
 */
std::vector<std::string> Graphe::listerSommetsAdjacents(const std::string& nom) const
{
	if (!(sommetExiste(nom))) throw std::logic_error("listerSommetsAdjacents: Le sommet n'existe pas");

	//On retourne l'adresse du sommet en argument
	std::vector<std::string> sommets;
	Sommet* courant = listeSommets;
	for (int i=0; i < nbSommets; i++)
	{
		if (courant->nom == nom)
		{
			Arc* arcCourant = courant->listeDest;
			while (arcCourant != 0)
			{
				sommets.push_back(arcCourant->dest->nom);
				arcCourant = arcCourant->suivDest;
			}
		}
		courant = courant->suivant;
	}
	return sommets;
}
/*
 * \fn int Graphe::nombreSommets() const
 * \return int le nombre de sommets du graphe
 */
int Graphe::nombreSommets() const
{
	return nbSommets;
}



/**
* \fn  int Graphe::nombreArcs() const
* \brief Retourne le nombre d'arc dans la liste des sommets
* \post Le graphe reste inchangé.
*/
int Graphe::nombreArcs() const
{
   return nbArcs;
}

/**
* \fn  Graphe::Sommet*  Graphe::_sommetAssocierAuNom(const std::string& nom)
* \brief retourne un pointeur vers le sommet correspondant au nom recu en param
* \return un pointeur vers le sommet qui correspond au nom recu en param
* \post Le graphe reste inchangé.
*/
Graphe::Sommet*  Graphe::_sommetAssocierAuNom(const std::string& nom)
{
   Sommet* courant = listeSommets;
   for (int j = 0; j < nbSommets; j++)
   {
      if(nom == courant->nom){
         return courant; // on donne la valeur en refence de courant a source
      }
      courant = courant->suivant;
   }
   return 0;
}

/*
 * \fn bool Graphe::estVide() const
 * \return true si le graphe est vide, false sinon
 */
bool Graphe::estVide() const
{
	return nbSommets == 0;
}

/*
 * \fn Ponderations Graphe::getPonderationsArc(const std::string& sommetUn, const std::string& sommetDeux) const
 * \param[in] sommetUn le premier sommet de l'arc
 * \param[in] somemtDeux le deuxieme sommet de l'arc
 * \return la ponderation de l'arc
 */
Ponderations Graphe::getPonderationsArc(const std::string& sommetUn, const std::string& sommetDeux) const
{
	if (!(arcExiste(sommetUn, sommetDeux))) throw std::logic_error("getPonderationsArc: L'arc n'existe pas");
	//On se positionne sur le bon sommet (sommetUn)
	Sommet* courant = listeSommets;
	while (courant->nom != sommetUn) courant = courant->suivant;
	//On se positionne sur le bon arc
	Arc* arcCourant = courant->listeDest;
	while (arcCourant->dest->nom != sommetDeux) arcCourant = arcCourant->suivDest;
	return arcCourant->ponder;
}

/*
 * \fn std::string Graphe::getNomSommet(float lt, float lg) const
 * \param[in] lt un float représentant la latitude du sommet
 * \param[in] un float représentant la longitude du sommet
 * \return le nom du sommet
 */
std::string Graphe::getNomSommet(float lt, float lg) const
{
	Sommet* courant = listeSommets;
	for (int i=0; i<nbSommets; i++)
	{
		if (courant->coord.lg == lg && courant->coord.lt == lt)
			return courant->nom;
		courant = courant->suivant;
	}
	throw std::logic_error("getNomSommet: Les coordonnees ne representent aucun sommet");
}

/*
 * \fn Coordonnees Graphe::getCoordonnesSommet(const std::string& nom) const
 * \param[in] nom le nom du sommet à trouver
 * \return les coordonnees du sommet trouvé
 */
Coordonnees Graphe::getCoordonnesSommet(const std::string& nom) const
{
	if (estVide()) throw std::logic_error("getCoordonneesSommet: Il n'y a aucun sommet dans le graphe");
	if (!(sommetExiste(nom))) throw std::logic_error("getCoordoneesSommet: le sommet n'existe pas");

	Sommet* courant = listeSommets;
	while (courant->nom != nom) courant = courant->suivant;
	return courant->coord;
}

/*
 * \fn float Graphe::getDistanceEucledienne(const std::string& sommetUn, const std::string& sommetDeux) const
 * \param[in] sommetUn le premier sommet
 * \param[in] sommetDeux le deuxieme sommet
 * \return la distance en float entre les deux sommets
 */
float Graphe::getDistanceEucledienne(const std::string& sommetUn, const std::string& sommetDeux) const
{
	if (!(sommetExiste(sommetUn)) && !(sommetExiste(sommetDeux))) throw std::logic_error("getDistanceEucledienne: un ou plusieurs sommets n'existent pas");

	Coordonnees un;
	Coordonnees deux;
	Sommet* courant = listeSommets;
	for (int i=0; i<nbSommets; i++)
	{
		if (courant->nom == sommetUn) un = courant->coord;
		else if(courant->nom == sommetDeux) deux = courant->coord;
		courant = courant->suivant;
	}
	const float distance = sqrt(pow((un.lg - deux.lg), 2.0) + pow((un.lt - deux.lt), 2.0));
	return distance;
}

/*
 * \fn void Graphe::detruireGraphe()
 */
void Graphe::detruireGraphe()
{
	Sommet* courant = listeSommets;
	for (int i=0; i<nbSommets; i++)
	{
		Arc* arcCourant = courant->listeDest;
		while (arcCourant != 0)
		{
			enleverArc(courant->nom, arcCourant->dest->nom);
			arcCourant = arcCourant->suivDest;
		}
		enleverSommet(courant->nom);
		courant = courant->suivant;
	}
	//on supprime les tableaux
	delete listeSommets;
}





//NEW
/*
 * \fn  int getNbSommets()
 * \brief retourne le nombre de sommets d'un graphe
 *
 * \return un int représentant lenombre de sommets du graphe
*/
int Graphe::getNbSommets()
{
   return nbSommets;
}


//NEW
/*
 * \fn  void setAllStateFalse()
 * \brief set l'état de tous les sommets du graphe à false
 *
 * \return void
*/
void Graphe::setAllStatesFalse()
{
   Sommet* courant = listeSommets;
   for (int i=0; i<nbSommets; i++)
   {
      courant->etat = false;
      courant = courant->suivant;
   }
}

//NEW
/*
 * \fn  void setState(const std::string& sommet, bool state)
 * \brief set l'état d'un sommet
 *
 * \param[in] sommet le sommet à modifier
 * \param[in] state le booléen représentat le nouvel état
 *
 * \return void
*/
void Graphe::setState(const std::string& sommet, bool state)
{
   Sommet* courant = listeSommets;
   while (courant->nom != sommet)
   {
      courant = courant->suivant;
   }
   courant->etat = state;
}

//NEW
/*
 * \fn  std::vector<std::string> getSuccesseurs(const std::string& origine)
 * \brief retourne un vector contenant les successeurs d'un sommet
 *
 * \param[in] origine le sommet recherché
 *
 * \return std::vector<std::string> un vector contenant les noms des sommets successeurs au sommet d'origine
*/
std::vector<std::string> Graphe::getSuccesseurs(const std::string& origine)
{
   std::vector<std::string> successeurs;
   Sommet* courant = listeSommets;
   while (courant->nom != origine)
   {
      courant = courant->suivant;
   }
   Arc* arcCourant = courant->listeDest;
   while (arcCourant != 0)
   {
      successeurs.push_back(arcCourant->dest->nom);
      arcCourant = arcCourant->suivDest;
   }
   return successeurs;
}

//NEW
/*
 * \fn  bool getState(const std::string& sommet)
 * \brief retourne l'état d'un sommet
 *
 * \param[in] sommet le sommet recherché
 *
 * \return bool l'état d'un sommet
*/
bool Graphe::getState(const std::string& sommet)
{
   Sommet* courant = listeSommets;
   while (courant->nom != sommet)
   {
      courant = courant->suivant;
   }
   return courant->etat;
}
}//Fin du namespace