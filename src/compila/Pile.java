package compila;
public class Pile{
 
    int sommet;
    int[] piles;
 
    Pile(int capacite){
      piles = new int[capacite];   //créer une nouvelle pile vide de taille maximal capacite
      sommet = -1;  //lorsque tu auras empiler un élément tu l'obtiendras à piles[0]
    }
 
    int getSommet(){
        return piles[sommet];
    }
 
    boolean estVide(){
      if(sommet == -1){
        return true;
      }
      return false;
    }
 
    boolean estPleine(){
      if(sommet == piles.length){
        return true;
      }
      return false;
    }
     
    void empile(int element){
      if (estPleine()){
        // new throw Error("La pile est pleine impossible d'empiler");
        System.out.println("Impossible de d'epiler");
      }
      piles[sommet+1] = element;
      sommet++;
      
    }
 
    void depile(){
      if (estVide()){
      // new throw Error("La pile est vide impossible de depiler");
          System.out.println("Impossible de depiler");
      }
      sommet--;  //inutile de la remplacer par une valeur 0 ou null car c'est avec l'indice sommet que tu y accèdes.
            //on retourne la valeur que nous avons depiler
    }
 
    String versChaine(){ //Pour visualiser ta pile
      if(estVide()){
        return "[]";
      }
      if(sommet == 0){
        return "["+getSommet()+"]";
      }
      return "["+piles[0]+" "+getSommet()+"]";
    }
}
