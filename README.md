#compila

#Binome

OUABDESSELAM MEZIANE

BOUKHATEB MOHAME ELAMINE 

 GROUPE 7

---------------

## Getting Started

Ce projet represent un compilateur qu'on a programmer qui est didiée au  langage compila ( extension .COMPILA )  

## Analyse Lexicale 

Bon on n'a commencer avec L'analyse Lexicale , on a utiliser 2 classes specialement pour cette partie donc il y'as la classe token.java

qui est de type enum on n'as mis tous les Tokens dans cette classe , aprés il y'as la classe lexicale qui utiliste un stringbuildere (input)

qui va recevoir tous le texte qu'on veut compiler et aprés cela j'ai fais une fonction qui efface tous les espaces qu'on peut ignorer

Ensuite , on utilise un matcher qui a éte déclarer dans la classe token.java pour examiner chaque caractére avec le token defenie .

Enfin , on execute ( on utilise la valeur exhausted comme conditon d'arret ) ainsi on va avoir notre analyse lexicale , 

en cas d'erreur il arrete le programme et il affiche l'erreur . 

## Analyse Syntaxique

Pour cette analyse on n'as declarer plusieurs expression réguleres comme string Ensuite on a utiliser un BufferReader pour lire 

Chaque Ligne du programme aprés on n'as tocker chaque ligne dans une case d'un tableau Ainsi on n'as comparer chaque ligne avec une 

Expression réguliere on n'as utliser une valeur booléennes comme cas d'érreurs . 

## Analyse Sémantique 

L'analyse Sémantique a éte assez reflechis par nous on n'as premierement utiliser la partie de l'analyse syntaxique mais en plus de 

ca on n'as ajouter quelques conditions(commencer par start_program ... finir par End_Program) aussi on n'as utiliser 

des piles ( classe Pile.java ) pour les equilibrer le nombre de else avec le nombre de if et meme le nombre de Start (DebutBloc) 

Avec le nombre de Finish ( Fin Bloc ) , Et les maps pour Les déclarations ( si on déclare deux fois ) et meme pour 

les fonctions Give , Affect  ( qu'on uilise des variables non déclaréée ) 

## Acknowledgments

"StackOverflow"
"Searching"
* etc
