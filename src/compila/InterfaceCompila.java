/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compila;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Meziane Ted
 */
public class InterfaceCompila extends javax.swing.JFrame {
    // ExpressionRéguliére er = new ExpressionRéguliére();

    protected JFileChooser fc = new JFileChooser();
    protected Map<Integer, String> Declaration = new HashMap<>();
    public int f = 0;
    public int Entier = 0;
    public int Réel = 0;

    /*Focntion pour la lécture d'un fichier */
 /*la lecture meme des lignes vide merci */
    public void read(File file) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
            String line = "";
            String s = "";
            String motsInteger = "Int_Number";
            String motsReal = "Real_Number";
            //  String[] Integer = new String[Entier];
            //  String[] Real = new String[Réel] ; 
            while ((line = br.readLine()) != null) {
                f = f + 1;
                s += line + "\n";
                String[] mots = line.split(" ");//Décomposer la ligne par les épaces

                if (motsInteger.contains(mots[0])) { //Int number (combien il y'as d'entiers )
                    if (mots[2].contains(",")) { //pour savoir est qu'il y'as plusieur identifiants ou non 
                        String[] MotAvecVirgule = mots[2].split(",");//le cas ou il y'as plusieurs identifiants onj les décomposes avec des virgules
                        for (int j = 0; j < MotAvecVirgule.length; j++) //parcourir les composants (identifiants)
                        {

                            if (j != MotAvecVirgule.length) {
                                //   System.out.println(MotAvecVirgule[j]);
                                Entier++;
                            }

                        }
                    } else {
                        Entier = 1;
                        //  System.out.println(mots[2]);
                    }

                }
                if (motsReal.contains(mots[0])) { //Real_number (combien il y'as de réel number )
                    if (mots[2].contains(",")) { //pour savoir est qu'il y'as plusieur identifiants ou non 
                        String[] MotAvecVirgule = mots[2].split(",");//le cas ou il y'as plusieurs identifiants onj les décomposes avec des virgules
                        for (int j = 0; j < MotAvecVirgule.length; j++) //parcourir les composants (identifiants)
                        {

                            if (j != MotAvecVirgule.length) {

                                Réel++;
                                //   System.out.println(MotAvecVirgule[j]);
                            }

                        }
                    } else {
                        Réel = 1;
                        //     System.out.println(mots[2]);
                    }

                }
            }
            Programme.setText(s);
            System.out.println("Le nombre d'entiers est de " + Entier);
            System.out.println("Le nombre de réel est de " + Réel);
            //fermer le buffer 
            if (br != null) {
                br.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Probleme dans le programme");
        }
    }

    /*Fonction pour la reconnaissance  */
 /*Revoir les Expressions(Matcher et Pattern) et changer le code si il y'as le temps */
    public void AnalyseLexicale(File file) {
        String Interface = "";
        Lexer lexer = new Lexer(file.getPath());

        System.out.println("Lexical Analysis");
        System.out.println("-----------------");
        while (!lexer.isExausthed()) {
            System.out.printf("%-18s :  %s \n", lexer.currentLexema(), lexer.currentToken());
            Interface = Interface + "" + lexer.currentLexema() + "" + lexer.currentToken() + "";
            lexer.moveAhead();
        }

        if (lexer.isSuccessful()) {
            System.out.println("Ok! 😀");
        } else {
            System.out.println(lexer.errorMessage());
        }

    }

    /*si on met n'importe des caracter au debut ou a la fin d'une instruction il y'as pas d'erreurs*///a regler.
    /*String et valeur*/
 /*Probleme de if(il ne li pas toute la ligne)Aprés un vancement important suer la semantique on yreviendra*/
    public void AnalyseSyntaxique(File file) {
        try {
            /*Expressions réguliers*/

            String Touslescaractéres = ".+";
            String Lettre = "[A-Za-z]";
            String Comparaison = "(<|>|<=|>=|=|!=)";
            String Chiffre = "[0-9]";
            String Identifiant = "(" + Lettre + ")+(_?(" + Lettre + "|" + Chiffre + ")+)*";
            String Number = "[0-9]+(.[0-9]+)?";
            String ChaineCaractére = "\"" + Touslescaractéres + "\\s\"";
            String Déclaration = "(Int_Number\\s:\\s(" + Identifiant + ",)*(" + Identifiant + ")\\s;;)|(Real_Number\\s:\\s(" + Identifiant + ",)*(" + Identifiant + ")\\s;;)|(String\\s:\\s(" + Identifiant + ",)*(" + Identifiant + ")\\s;;)";
            String Fonction = "Give\\s" + Identifiant + "\\s:\\s" + Number + "|" + ChaineCaractére + "\\s;;|Affect\\s" + Identifiant + "\\sto\\s" + Identifiant + "\\s;;"; //aprés il faut preciser les nombre réels et entier
            String Commentaire = "//.\\s" + Touslescaractéres + "";
            String Affichage = "ShowVal\\s:\\s" + Identifiant + "\\s;;";
            String AffichageMessage = "ShowMes\\s:\\s\"" + Touslescaractéres + "\\s\"";
            String Condition = "If\\s--\\s" + Identifiant + Comparaison + Identifiant + "\\s--\\s" + Fonction; //ici probléme (facile a regler normalement)   
            String DebutBloc = "Start"; //  
            String FinBloc = "Finish";
            String Else = "Else";
            String Predebut = Déclaration + "|" + AffichageMessage + "|" + DebutBloc + "|" + Commentaire;
            String millieu = "Start_Program|" + Predebut + "|" + FinBloc + "|" + Fonction + "|" + Condition + "|" + Affichage + "|" + Else + "|End_Program";

            /*Fin des Expressions réguliers*/
            String ligne = "";
            String[] code = new String[f];
            boolean stop = false;
            int i = 0;
            BufferedReader Syntax = new BufferedReader(new FileReader(file.getPath()));
            while ((ligne = Syntax.readLine()) != null) {
                code[i] = ligne;
                System.out.println(code[i]);
                i++;
            }

            for (i = 0; i < f; i++) {
                try {
                    Pattern pmillieu = Pattern.compile(millieu);
                    Matcher mmillieu = pmillieu.matcher(code[i]);
                    String[] mots = code[i].split(" ");
                    if (mmillieu.find()) { // changer ca en while en cas de probléme 

                        System.out.println(code[i].substring(mmillieu.start(), mmillieu.end()));
                        stop = true;

                    }
                } catch (PatternSyntaxException pse) {
                    System.out.println("Chawala hada ");
                }
                if (!stop) {
                    System.out.println(code[i] + "\tErreur de syntax ici");
                }
                stop = false;
            }

//            Programme.setText(s); 
            if (Syntax != null) {
                Syntax.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Probleme dans le programme");
        }

    }

    /*Fonction pour la logique*/
    /*Derniére Etape Analyse des affectations (variables -> variables ) && (variables -> valeur ) */
    public void AnalyseSémantique(File file) 
    // Pobleme d'erreur sémantique entre Start_Program et Start par ce que il s'on foue de la fin
    {
        try {
            /*LES Expressions réguliers*/

            String Touslescaractéres = ".+";
            String Lettre = "[A-Za-z]";
            String Comparaison = "(<|>|<=|>=|=|!=)";
            String Chiffre = "[0-9]";
            String Identifiant = "(" + Lettre + ")+(_?(" + Lettre + "|" + Chiffre + ")+)*";
            String Realnumber = "[0-9]+.[0-9]+";
            String Integernumber = "[0-9]+";
            String Number = "[0-9]+(.[0-9]+)?";
            String ChaineCaractére = "\"" + Touslescaractéres + "\\s\"";
            String Déclaration = "(Int_Number\\s:\\s(" + Identifiant + ",)*(" + Identifiant + ")\\s;;)|(Real_Number\\s:\\s(" + Identifiant + ",)*(" + Identifiant + ")\\s;;)|(String\\s:\\s(" + Identifiant + ",)*(" + Identifiant + ")\\s;;)";
            String Fonction = "Give\\s" + Identifiant + "\\s:\\s" + Number + "|" + ChaineCaractére + "\\s;;|Affect\\s" + Identifiant + "\\sto\\s" + Identifiant + "\\s;;";
            String Commentaire = "//.\\s" + Touslescaractéres + "";
            String Affichage = "ShowVal\\s:\\s" + Identifiant + "\\s;;";
            String AffichageMessage = "ShowMes\\s:\\s\"" + Touslescaractéres + "\\s\"";
            String Condition = "If\\s--\\s" + Identifiant + Comparaison + Identifiant + "\\s--\\s" + Fonction;
            String DebutBloc = "Start"; // il faut limiter les start au fonctiuon de finsh  ( l'analyse sémantique ) 
            String FinBloc = "Finish";
            String Else = "Else";
            String Predebut = Déclaration + "|" + AffichageMessage + "|" + DebutBloc + "|" + Commentaire;
            String millieu = Predebut + "|" + FinBloc + "|" + Fonction + "|" + Condition + "|" + Affichage + "|" + Else;
            String ligne = "";
            String[] code = new String[f]; // vaut mieu l'optimiser aprés par une variable globale

            boolean stop = false;
            boolean Erreur = true;
            boolean Erreur2 = true;
            int i = 0;
            BufferedReader Syntax = new BufferedReader(new FileReader(file.getPath()));
            Pile pileBloc = new Pile(200);
            Pile pileCondition = new Pile(200);

            while ((ligne = Syntax.readLine()) != null) {
                code[i] = ligne;
                System.out.println(code[i]);
                i++;
            }

            try {
                Pattern pdebut = Pattern.compile("Start_Program"); // le debut du programme 
                Matcher mdebut = pdebut.matcher(code[0]); //ameliorere le detection d'erreurs
                while (mdebut.find()) {
                    System.out.println(code[0].substring(mdebut.start(), mdebut.end())); //exception de null pointer ou probleme de matcher
                    stop = true;
                    Erreur = false;
                }

            } catch (PatternSyntaxException pse) {
                System.out.println("Chawala hada ");
            }
            if (!stop) {
                System.out.println(code[0] + "\tErreur de syntax ici");
            }
            stop = false;
            //pour les cas de non reconnaisance du programme

            if (!Erreur) {
                try { //deuxieme ligne
                    //ajouter le cas de start dans la deuxieme ligne
                    Pattern pprédebut = Pattern.compile(Predebut); // la dexueieme ligne  du programme 
                    Matcher mprédebut = pprédebut.matcher(code[1]); //ameliorere le detection d'erreurs
                    String[] mots = code[1].split(" ");
                    while (mprédebut.find()) {//ajouter les conditons le meme truc que predebut sur les declarations

                        if (mots[0].equals(DebutBloc)) {
                            pileBloc.empile(1);
                            System.out.println(code[1].substring(mprédebut.start(), mprédebut.end()));
                            stop = true;
                        } else if (mots[0].equals("Int_Number")) { //Verifier 
                            stop = true;
                            int k = 0;
                            System.out.println(code[1].substring(mprédebut.start(), mprédebut.end()));
                            if (mots[2].contains(",")) {
                                String[] MotAvecVirgule = mots[2].split(",");
                                for (int j = 0; j < MotAvecVirgule.length; j++) {
                                    if (!(Declaration.values().contains(MotAvecVirgule[k]))) {
                                                Declaration.put(k, MotAvecVirgule[j]);
                                                k++;
                                                
                                            } else {
                                                
                                                System.out.println(MotAvecVirgule[k] + "  Deja déclareé desolé");
                                                }
                                       }

                            } else {
                                        if (!(Declaration.values().contains(mots[2]))) {

                                        
                                        Declaration.put(1, mots[2]);

                                    } else {
                                        
                                        System.out.println(mots[2] + "  Deja déclareé desolé");
                                    }
                            }
                            
                        } else if (mots[0].equals("Real_Number")) { //Verifier 
                            stop = true;
                            System.out.println(code[1].substring(mprédebut.start(), mprédebut.end()));
                            if (mots[2].contains(",")) {
                                String[] MotAvecVirgule = mots[2].split(",");
                                int k = 0;
                                for (int j = 0; j < MotAvecVirgule.length; j++) {
                                    if (!(Declaration.values().contains(MotAvecVirgule[k]))) {
                                                Declaration.put(k, MotAvecVirgule[j]);
                                                k++;
                                                
                                            } else {
                                                
                                                System.out.println(MotAvecVirgule[k] + "  Deja déclareé desolé");
                                                }
                                       }

                            } else {
                                        if (!(Declaration.values().contains(mots[2]))) {

                                        
                                        Declaration.put(1, mots[2]);

                                    } else {
                                        
                                        System.out.println(mots[2] + "  Deja déclareé desolé");
                                    }
                            }
                        } else if (mots[0].equals("String")) { //Verifier 
                            stop = true;
                            int k = 0;
                            System.out.println(code[1].substring(mprédebut.start(), mprédebut.end()));
                            if (mots[2].contains(",")) {
                                String[] MotAvecVirgule = mots[2].split(",");
                                for (int j = 0; j < MotAvecVirgule.length; j++) {
                                    if (!(Declaration.values().contains(MotAvecVirgule[k]))) {
                                                Declaration.put(k, MotAvecVirgule[j]);
                                                k++;
                                                
                                            } else {
                                                
                                                System.out.println(MotAvecVirgule[k] + "  Deja déclareé desolé");
                                                }
                                       }

                            } else {
                                        if (!(Declaration.values().contains(mots[2]))) {

                                        
                                        Declaration.put(1, mots[2]);

                                    } else {
                                        
                                        System.out.println(mots[2] + "  Deja déclareé desolé");
                                    }
                            }
                        }
                    }

                } catch (PatternSyntaxException pse) {
                    System.out.println("Chawala hada ");
                }
                if (!stop) {
                    System.out.println(code[1] + "\tErreur de syntax ici");
                }
                //pour les cas de non reconnaisance du programme

                for (int j = 2; j < f - 1; j++)//pour l'analyse syntaxique du milieu
                {
                    try {
                        //probleme ici
                        Pattern pmillieu = Pattern.compile(millieu); // le debut du programme 
                        Matcher mmillieu = pmillieu.matcher(code[j]); //ameliorere le detection d'erreurs
                        String[] mid = code[j].split(" ");
                        while (mmillieu.find()) {

                            if (mid[0].equals("If")) {
                                pileCondition.empile(1);
                                System.out.println(code[j].substring(mmillieu.start(), mmillieu.end())); // c'est pas grave par ce que je l'ai diviser c pour ca qui pose un probléme
                                stop = true;

                            } else if (mid[0].equals(Else)) {
                                if (!pileCondition.estVide()) {

                                    pileCondition.depile();
                                    System.out.println(code[j].substring(mmillieu.start(), mmillieu.end()));
                                    stop = true;
                                } else {
                                    stop = false;
                                }

                            } else if (code[j].contains(DebutBloc)) {
                                System.out.println(code[j].substring(mmillieu.start(), mmillieu.end())); //exception de null pointer ou probleme de matcher
                                pileBloc.empile(1);
                                if (pileBloc.estVide()) {
                                    System.out.println("Olalala y'as pas de start");
                                }
                                stop = true;

                            } else if (code[j].contains(FinBloc)) {
                                if (pileBloc.estVide()) {
                                    stop = false;

                                } else {
                                    pileBloc.depile();
                                    System.out.println(code[j].substring(mmillieu.start(), mmillieu.end()));
                                    stop = true;
                                }

                            } else if (mid[0].equals("Int_Number")) { //Verifier 
                                stop = true;
                                System.out.println(code[j].substring(mmillieu.start(), mmillieu.end()));
                                if (mid[2].contains(",")) {
                                    String[] MotAvecVirgule = mid[2].split(",");
                                    int Integer = 100;
                                    
                                    
                                        for (int k = 0; k < MotAvecVirgule.length; k++) {
                                                
                                            if (!(Declaration.values().contains(MotAvecVirgule[k]))) {
                                                Declaration.put(Integer, MotAvecVirgule[k]);
                                                Integer++;
                                                
                                            } else {
                                                
                                                System.out.println(MotAvecVirgule[k] + "  Deja déclareé desolé");
                                            }
                                            

                                        }
                                    
                                                
                                                
                                            
                                } else {
                                    if (!(Declaration.values().contains(mid[2]))) {

                                        
                                        Declaration.put(100, mid[2]);

                                    } else {
                                        
                                        System.out.println(mid[2] + "  Deja déclareé desolé");
                                    }

                                }
                            } else if (mid[0].equals("Real_Number")) { //Verifier 
                                stop = true;
                                System.out.println(code[j].substring(mmillieu.start(), mmillieu.end()));
                                if (mid[2].contains(",")) {
                                    String[] MotAvecVirgule = mid[2].split(",");
                                    int Real = 200;
                                    for (int k = 0; k < MotAvecVirgule.length; k++) {
                                        if (!(Declaration.values().contains(MotAvecVirgule[k]))) {
                                            
                                            Declaration.put(Real, MotAvecVirgule[k]);
                                            Real++;
                                        } else {
                                            
                                            System.out.println(MotAvecVirgule[k] + "  Deja déclareé desolé");
                                        }

                                    }
                                    
                                               
                                               
                                            

                                } else {
                                    if (!(Declaration.values().contains(mid[2]))) {
                                        
                                        Declaration.put(200, mid[2]);
                                    } else {
                                        
                                        System.out.println(mid[2] + "  Deja déclareé desolé");
                                    }
                                }
                            } else if (mid[0].equals("String")) { //Verifier 
                                stop = true;
                                System.out.println(code[1].substring(mmillieu.start(), mmillieu.end()));
                                if (mid[2].contains(",")) {
                                    String[] MotAvecVirgule = mid[2].split(",");
                                    for (int k = 0; k < MotAvecVirgule.length; k++) {
                                        int Str = 300;
                                        if (!(Declaration.values().contains(MotAvecVirgule[k]))) {
                                            Declaration.put(Str, MotAvecVirgule[k]);
                                            
                                            Str++;
                                        } else {
                                            System.out.println(MotAvecVirgule[k] + "  Deja déclareé desolé");
                                        }

                                    }

                                } else {
                                    if (!(Declaration.values().contains(mid[2]))) {
                                        Declaration.put(300, mid[2]);
                                        

                                    } else {
                                        
                                        System.out.println(mid[2] + "  Deja déclareé desolé");
                                    }
                                }
                            } else {
                                System.out.println(code[j].substring(mmillieu.start(), mmillieu.end())); //exception de null pointer ou probleme de matcher
                                stop = true;
                            }

                        }
                    } catch (PatternSyntaxException pse) {
                        System.out.println("Chawala hada ");
                    }
                    if (!stop) {
                        System.out.println(code[j] + "\tErreur Sémantique ici(ou bien syntaxique )");
                    }
                    stop = false;
                }
            }

            try {
                Pattern pfin = Pattern.compile("End_Program"); // la fin du programme 
                Matcher mfin = pfin.matcher(code[f - 1]);
                while (mfin.find()) {
                    System.out.println(code[f - 1].substring(mfin.start(), mfin.end()));
                    stop = true;
                }
            } catch (PatternSyntaxException pse) {
                System.out.println("Chawala hada ");
            }

            if (!stop) {
                System.out.println(code[f - 1] + "\tErreur de syntax ici");
            }
            stop = false;
            //Programme.setText(s); 
            //fermer le buffer 
            if (Syntax != null) { // ??
                Syntax.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Probleme dans le programme");
        }
    }

    /**
     * Creates new form InterfaceCompila
     */
    public InterfaceCompila() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        textArea1 = new java.awt.TextArea();
        jPanel1 = new javax.swing.JPanel();
        Cuf = new javax.swing.JLabel();
        Al = new javax.swing.JLabel();
        As = new javax.swing.JLabel();
        Asé = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        Programme = new java.awt.TextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Mon Analyseur Lexical , syntaxique et sémantique");

        Cuf.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Cuf.setText("Charger un fichier");
        Cuf.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Cuf.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Cuf.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CufMouseClicked(evt);
            }
        });

        Al.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Al.setText("Analyse syntaxique");
        Al.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Al.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Al.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AlMouseClicked(evt);
            }
        });

        As.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        As.setText("Analyse sémantique");
        As.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        As.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        As.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AsMouseClicked(evt);
            }
        });

        Asé.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Asé.setText("Analyse lexicale");
        Asé.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Asé.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Asé.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AséMouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Sortie :");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Commandes");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(Cuf, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Al, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                        .addComponent(As, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                        .addComponent(Asé, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 89, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Programme, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Cuf, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(Asé, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Al, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(As, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Programme, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void CufMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CufMouseClicked

        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Fichier Compila", "Compila");
        fc.setFileFilter(filter);
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            read(file);

            //Desktop.getDesktop().browse("Dictionnaire.txt");
            /*
             FileWriter fw = new FileWriter (file.getPath());
            fw.write(content);
            fw.flush();
            fw.close();
             */

    }//GEN-LAST:event_CufMouseClicked
    }
    private void AséMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AséMouseClicked
        // LETS GO FOR  ( ANALYSE LEXICALE )
        File file = fc.getSelectedFile();
        AnalyseLexicale(file);
    }//GEN-LAST:event_AséMouseClicked

    private void AlMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AlMouseClicked
        // LETS GO FOR THE NEXT ONE ( ANALYSE SYNTAXIQUE )
        File file = fc.getSelectedFile();
        AnalyseSyntaxique(file);
    }//GEN-LAST:event_AlMouseClicked

    private void AsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AsMouseClicked
        // LETS GO FOR THE NEXT ONE ( ANALYSE SEMANTIQUE )
        File file = fc.getSelectedFile();
        AnalyseSémantique(file);
    }//GEN-LAST:event_AsMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InterfaceCompila.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InterfaceCompila.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InterfaceCompila.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InterfaceCompila.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InterfaceCompila().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Al;
    private javax.swing.JLabel As;
    private javax.swing.JLabel Asé;
    private javax.swing.JLabel Cuf;
    private java.awt.TextArea Programme;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private java.awt.TextArea textArea1;
    // End of variables declaration//GEN-END:variables

    private Object open(File file) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
