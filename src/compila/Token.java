/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compila;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Token {//a changer les commentaires et les messages
    Symbole_Inferieur ("<"),
    Symbole_Inferieur_ou_égale ("<="),
    Symbole_superieur(">"),
    Symbole_superieur_ou_égale (">="), 
    Symbole_égalité ("="),
    Mot_Reserve_fin_bloc("Finish"),
    Mot_reserve_debut_dun_programme("Start_Program"),
    Mot_Reserve_debut_bloc("Start"),
    Mot_reservé_declaration_dun_entier ("Int_Number"),
    Mot_Reservé_déclaration_dun_réel ("Real_Number"),
    Mot_Reservé_déclaration_dun_caracter ("String"),
    Caractére_reserve (":"),
    Caractére_Virgule (","),
    Mot_reservé_Give("Give"),
    Mot_reservé_Affectation("Affect"),
    Mot_reservé_Message("ShowMes"),
    Mot_reservé_Valeur("ShowVal"),
    Mot_Reserve_Affectation_to("to"),
    Mot_reservé_Fin_Instruction(";;"),
    Mot_reservé_pour_une_condition_If ("If"),
    Mot_reservé_pour_une_condition_Else ("Else"), 
    Mot_reservé_pour_une_condition ("--"), 
    Message("\"[^\"]*\""),
    Commentaire("//.\\s.+\\s"),
    Mot_reserve_fin_dun_programme("End_Program"),
    Valeur_Reel("[0-9]+.[0-9]+"),
    Valeur_Entier ("[0-9]+"),
    Valeur_String ("'[^']*'"),
    Identificateur ("([A-Za-z])+(_?([A-Za-z]|[0-9])+)*");

    private final Pattern pattern;
    
    Token(String regex) {
        pattern = Pattern.compile("^" + regex);
    }

    int FinduMatcher(String s) {
        Matcher m = pattern.matcher(s);

        if (m.find()) {
            return m.end();
        }
        return -1;
    }
}
