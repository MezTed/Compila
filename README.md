#compila

#Binome

OUABDESSELAM MEZIANE

BOUKHATEB MOHAMED EL AMINE 

 GROUPE 7

---------------

## Commencement

Ce projet represent un compilateur qu'on a programmer qui est dédié au  langage compila ( extension .COMPILA )  

## Analyse Lexicale 

Bon on n'a commencer avec L'analyse Léxicale , on a utiliser 2 classes spécialement pour cette partie donc il y'as la classe token.java

qui est de type enum on n'as mis tous les Tokens dans cette classe , aprés il y'as la classe léxicale qui utilise un StringBuilder (input)

qui va recevoir tous le programme qu'on veut compiler et aprés cela j'ai fais une fonction qui éfface tous les espaces qu'on peut ignorer.

Ensuite , on utilise un matcher qui a été déclarer dans la classe token.java pour éxaminer chaque caractére avec le token définie .

Enfin , on éxecute ( on utilise la valeur exhausted comme conditon d'arret ) ainsi on va avoir notre léxicale , 

en cas d'erreur il arrete le programme et il affiche l'erreur . 

## Analyse Syntaxique

Pour cette analyse on n'as declarer plusieurs expressions réguliéres comme string Ensuite on a utiliser un BufferReader pour lire 

Chaque Ligne du programme aprés on n'as stocker chaque ligne dans une case d'un tableau , Ainsi on n'as comparer chaque ligne avec chaque 

expression réguliere et  on n'as utliser une valeur booléennes comme cas d'érreurs . 

## Analyse Sémantique 

L'analyse Sémantique a éte assez réflechis par nous on n'as premierement utiliser la partie de l'analyse syntaxique mais en plus de 

ca on n'as ajouter quelques conditions (commencer par start_program ... finir par End_Program) aussi on n'as utiliser 

des piles ( classe Pile.java ) pour equilibrer le nombre de else avec le nombre de if et meme le nombre de Start (DebutBloc) 

Avec le nombre de Finish ( Fin Bloc ) , Ensuite on n'as utiliser la classe maps pour Les déclarations ( si on déclare deux fois ) 

et meme pour les fonctions Give , Affect  ( qu'on uilise des variables non déclaréée ) 

## Recherches

"https://stackoverflow.com/"
"https://github.com/"
"https://www.geeksforgeeks.org/"
* etc
## Contstruit Avec 

"Netbeans 8.2"
"Java Language"

