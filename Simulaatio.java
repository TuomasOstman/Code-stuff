import java.io.*;
import apulaiset.*;
import fi.uta.csjola.oope.lista.*;
import Lotkoja.*;

/**Simulaation luokka, joka simuloi lotkojen elämää alkulimassa
  *@author Tuomas Östman
  *
  *This is a part of my coursework
  *Rest of the work was partly code from the course so can't upload that.
  */
  
public class Simulaatio{
   
   //Vakioita
   final static String LATAA = "lataa";
   final static String LISTAA = "listaa";
   final static String LIIKU = "liiku";
   final static String LUO = "luo";
   final static String TALLENNA = "tallenna";
   final static String LOPETA = "lopeta";
   final static char EROTIN = '|';
   final static String VIRHE = "Virhe!";
   //Attribuutteja
   /**Lista johon talletetaan lotkot */
   static OmaLista lotkoLista;
   
   /**Lista johon laitetaan tietyssä paikassa olevat Lötköt*/
   static OmaLista paikanLotkot;

   /**Maksimi x koordinaatti */
   static int xmax;
   /**Maksimi y koordinaatti */
   static int ymax;
   /**Uuden lotkon tuleva indeksi */
   static int indeksi = 0;
   
   /**Metodi joka lukee komennot ja jossa on itse ohjelma pyöritys
     */
   public static void simulointi(){
      String komento;  
      boolean lopeta = false;
      lataa();
      //luuppi jossa ajetaan komennot
      while(!lopeta){
         
         System.out.println("Kirjoita komento:");
         komento = In.readString();
         
         String[] komennonOsat = komento.split("[ ]");
         //Jos oikea syote niin jateketaan ja kokeillaan myös että heitteleeko virheita
         try{
            if(komennonOsat[0].equals(LATAA) || komennonOsat[0].equals(LISTAA) || komennonOsat[0].equals(LIIKU) ||
               komennonOsat[0].equals(LUO) || komennonOsat[0].equals(TALLENNA) || komennonOsat[0].equals(LOPETA)){
               //jos lautaus komento
               if(komennonOsat[0].equals(LATAA)){
                  if(komennonOsat.length == 1){
                     lataa();
                  }
                  else{
                     System.out.println(VIRHE);
                  }
               }
               //jos lopetus komento
               if(komennonOsat[0].equals(LOPETA)){
                  if(komennonOsat.length == 1){
                     lopeta = true;
                     System.out.println("Ohjelma lopetettu.");
                  }
                  else{
                     System.out.println(VIRHE);
                  }
               }
               //jos listaus komento ja jaettu sen mukaan miten on argumentteja
               if(komennonOsat[0].equals(LISTAA)){
                  if(komennonOsat.length == 1){
                     listaa();
                  }
                  else if(komennonOsat.length == 2){
                     listaa(Integer.parseInt(komennonOsat[1]));
                  }
                  else if(komennonOsat.length == 3){
                     listaa(Integer.parseInt(komennonOsat[1]),Integer.parseInt(komennonOsat[2]));
                  }
                  else{
                     System.out.println(VIRHE);
                  }
               }
               //jos liikkumis komento
               if(komennonOsat[0].equals(LIIKU)){
                  if(komennonOsat.length == 1){
                     liiku();
                  }
                  else if(komennonOsat.length == 4){
                     liiku(Integer.parseInt(komennonOsat[1]),Integer.parseInt(komennonOsat[2]),
                     Integer.parseInt(komennonOsat[3]));
                  }
                  else{
                     System.out.println(VIRHE);
                  }
               }
               if(komennonOsat[0].equals(TALLENNA)){
                  if(komennonOsat.length == 1){
                     tallenna();
                  }
                  else{
                     System.out.println(VIRHE);
                  }
               }
               if(komennonOsat[0].equals(LUO)){
                  if(komennonOsat.length == 1){
                     luo();
                  }
                  else{
                     System.out.println(VIRHE);
                  }
               }
            }
            //Muuten tulee virhe
            else{
               System.out.println(VIRHE);  
            }
         }
         //Jos yritta vaikka kirjainta listaa komentoon
         catch(NumberFormatException e){
            System.out.println(VIRHE);
         }
      }
   }
   
   
   /**Metodi joka lataa Lotkot tiedostota keskusmuistiin listaan
     */
   public static void lataa(){
      String[] lotkot = new String[1000];
      String[] tiedostoOsat = new String[5000];
      xmax = 0;
      ymax = 0;
      int[] paikk;
      lotkoLista = new OmaLista();
      indeksi = 0;
      try{
         //Tiedoston luentaa
         FileInputStream lotkoja = new FileInputStream("lotkot.txt");
         InputStreamReader lukija = new InputStreamReader(lotkoja);
         BufferedReader puskuroituLukija = new BufferedReader(lukija);
         
         //luetaan niin kauan kun luettavaa 
         int i = 0;
         while(puskuroituLukija.ready()){
            lotkot[i] = puskuroituLukija.readLine();
            i++;
         
         }

         //luetaan lotkot listaan
         for(i = 0; i < lotkot.length; i++){
            if(lotkot[i] == null){
               break;
            }
            tiedostoOsat = lotkot[i].split("["+EROTIN+"]");
            
            for(int j = 0; j < tiedostoOsat.length;j++){
               if(tiedostoOsat[j] == null){
                  break;
               }
               tiedostoOsat[j] = tiedostoOsat[j].trim();
            }
            if(i==0){
               xmax = Integer.parseInt(tiedostoOsat[1]);
               ymax = Integer.parseInt(tiedostoOsat[2]);
               int siemen = Integer.parseInt(tiedostoOsat[0]);
               Automaatti.alusta(siemen);
            }
            if(i>=0){
               //jos plantti niin tehdaan sille paikka ja laitetaan listaan
               if(tiedostoOsat[0].equals("Plantti")){
                  paikk = Automaatti.annaPaikka(xmax,ymax);
                  Paikka paikka = new Paikka(paikk[0],paikk[1]);
                  Plantti plantti = new Plantti(Integer.parseInt(tiedostoOsat[1]),tiedostoOsat[2],
                  Boolean.parseBoolean(tiedostoOsat[3]),paikka,indeksi);
                  lotkoLista.lisaaLoppuun(plantti);
                  indeksi++;
               }
               //jos klimppi niin tehdaan sille paikka ja laitetaan listaan
               if(tiedostoOsat[0].equals("Klimppi")){
                  paikk = Automaatti.annaPaikka(xmax,ymax);
                  Paikka paikka = new Paikka(paikk[0],paikk[1]);
                  Klimppi klimppi = new Klimppi(Integer.parseInt(tiedostoOsat[1]),tiedostoOsat[2],
                  tiedostoOsat[3].charAt(0),paikka,indeksi);
                  lotkoLista.lisaaLoppuun(klimppi);
                  indeksi++;
               }
            }
            
            //Etta siemenluku ja muut saadaan messiin
            if(lotkoLista != null){
               lotkoLista.lisaaAlkuun(lotkot[0]);
            }
         }
      }
      catch(FileNotFoundException e){
         System.out.println("tiedostohukku");
      }
      catch(IOException e){
         System.out.println("lukuvirhe");
      }
   }
   
   /**Metodi joka listaa kaikki lotkot
     */
   public static void listaa(){

      //Luetaan Myös simenluku ja koordinaatit
      String tiedot = (String)lotkoLista.alkio(0);
      String[] tiedonOsat = tiedot.split("["+EROTIN+"]");
      for(int j = 0; j < tiedonOsat.length;j++){
         tiedonOsat[j] = tiedonOsat[j].trim();
      }
      System.out.println(valeja(tiedonOsat[0],3)+EROTIN+valeja(tiedonOsat[1],3)
      +EROTIN+valeja(tiedonOsat[2],3)+EROTIN);
      
      //Tulostetaan Lotkot
      for(int i=0;i<=lotkoLista.koko();i++){
         if(lotkoLista.alkio(i) != null){
            //tulostetaan klimppi  
            if(lotkoLista.alkio(i) instanceof Klimppi){
               Klimppi klimppi = (Klimppi)lotkoLista.alkio(i);
               System.out.println(klimppi.toString(lotkoLista.alkio(i)));
 
            }
            //tulostetaan plantti
            if(lotkoLista.alkio(i) instanceof Plantti){
               Plantti plantti = (Plantti)lotkoLista.alkio(i);
               System.out.println(plantti.toString(lotkoLista.alkio(i)));
               
            }
         }
      }
   }
   
   /**Metodi listaa valitun lotkon equals samanlaiset
     *@param i valitun lotkon indeksi
     *
     */
   public static void listaa(int i){
      Plantti samaPlantti = null;
      Klimppi samaKlimppi = null;
      if(i >= indeksi){
         System.out.println(VIRHE);
      }
      else{
      for(int j=0;j<=lotkoLista.koko();j++){
      
         if(lotkoLista.alkio(j) instanceof Klimppi){
         
            Klimppi klimppi = (Klimppi)lotkoLista.alkio(j);
            //Otetaan oikea klimppi talteen
            if(klimppi.ind() == i){
               samaKlimppi = klimppi;
            }
            //tulostellaan samanlaiset ulos
            if(samaKlimppi != null){
            
               for(int k=0;k<=lotkoLista.koko();k++){
            
                  if(lotkoLista.alkio(k) instanceof Klimppi){
                     
                     klimppi = (Klimppi)lotkoLista.alkio(k);
                        
                     if(klimppi.equals(samaKlimppi)){
                        klimppi = (Klimppi)lotkoLista.alkio(k);
                        System.out.println(klimppi.toString(lotkoLista.alkio(k)));
                           
                     }
                  }
               }
               break;
            }
         }
         
         if(lotkoLista.alkio(j) instanceof Plantti){
         
            Plantti plantti = (Plantti)lotkoLista.alkio(j);
            
            //Otetaan oikea plantti talteen
            if(plantti.ind() == i){
               samaPlantti = plantti;
            }
            //tulostellaan samanlaiset
            if(samaPlantti != null){
            
               for(int k=0;k<=lotkoLista.koko();k++){
            
                  if(lotkoLista.alkio(k) instanceof Plantti){
                     
                     plantti = (Plantti)lotkoLista.alkio(k);
                        
                     if(plantti.equals(samaPlantti)){
                        plantti = (Plantti)lotkoLista.alkio(k);
                        System.out.println(plantti.toString(lotkoLista.alkio(k)));
                           
                     }
                  }
               }
               break;
            }
         }
      }
      }
   }
   
   /**Metodi joka listaa valitussa paikassa olevat lotkot
     *@param x x-koordinaatti
     *@param y y-koordinaatti
     */
   public static void listaa(int x, int y){
      if(x > xmax || y > ymax){
         System.out.println(VIRHE);
      }
      else{
         for(int i=0;i<=lotkoLista.koko();i++){

            if(lotkoLista.alkio(i) instanceof Klimppi){
               Klimppi klimppi = (Klimppi)lotkoLista.alkio(i);
               Paikka paikka = klimppi.paikka();
               if(x == paikka.xkoord() && y == paikka.ykoord()){
                  System.out.println(klimppi.toString(lotkoLista.alkio(i)));
               }
            }
            if(lotkoLista.alkio(i) instanceof Plantti){
               Plantti plantti = (Plantti)lotkoLista.alkio(i);
               Paikka paikka = plantti.paikka();
               if(x == paikka.xkoord() && y == paikka.ykoord()){
                  System.out.println(plantti.toString(lotkoLista.alkio(i)));
               }
            }
         }
      }
   }
   
   /**Metodi joka liikuttaa kaikkia lotkoja satunnaiseen viereiseen paikkaan
     */
   public static void liiku(){
      for(int i=0;i<=lotkoLista.koko();i++){
         int[] koord = new int[2];
         //jos klimppi
         if(lotkoLista.alkio(i) instanceof Klimppi){
            Klimppi klimppi = (Klimppi)lotkoLista.alkio(i);
            Paikka paikka = klimppi.paikka();
            koord = Automaatti.annaPaikka(paikka.xkoord(), paikka.ykoord(),xmax,ymax);
            Paikka uusiPaikka = new Paikka(koord[0],koord[1]);
            klimppi.paikka(uusiPaikka);
            lotkoLista.korvaa(i,klimppi);
         }
         //jos plantti
         if(lotkoLista.alkio(i) instanceof Plantti){
            Plantti plantti = (Plantti)lotkoLista.alkio(i);
            Paikka paikka = plantti.paikka();
            koord = Automaatti.annaPaikka(paikka.xkoord(), paikka.ykoord(),xmax,ymax);
            Paikka uusiPaikka = new Paikka(koord[0],koord[1]);
            plantti.paikka(uusiPaikka);
            lotkoLista.korvaa(i,plantti);
         } 
      }
   }
   
   /**Metodi joka liikuttaa valitun lotkon valittuun paikkaan
     *@param i valitun lotkon indeksi
     *@param x valitun paikan x-koordinaatti
     *@param y valitun paikan y-koordinaatti
     */
   public static void liiku(int i, int x, int y){
      //muuttuja lotkon indeksille
      int ind;
      //Katellaan onko haluttu paikka mukana
      if(x > xmax || y > ymax || i >= indeksi){
         System.out.println(VIRHE);
      }
      //jos on niin sitten jatketaan
      else{
         for(int j = 0; j <= lotkoLista.koko(); j++){
            if(lotkoLista.alkio(j) instanceof Klimppi){
               Klimppi klimppi = (Klimppi)lotkoLista.alkio(j);
               ind = klimppi.ind();
               if(ind == i){
                  Paikka uusiPaikka = new Paikka(x,y);
                  klimppi.paikka(uusiPaikka);
                  lotkoLista.korvaa(j,klimppi);
               }
            }
            if(lotkoLista.alkio(j) instanceof Plantti){
               Plantti plantti = (Plantti)lotkoLista.alkio(j);
               ind = plantti.ind();
               if(ind == i){
                  Paikka uusiPaikka = new Paikka(x,y);
                  plantti.paikka(uusiPaikka);
                  lotkoLista.korvaa(j,plantti);
               }
            }
         }
      } 
   }
   
   /**Metodi joka aloittaa lotkojen lisaantymisen
     *
     */
   public static void luo(){
      // Vähän plänttejä ja klimppejä joilla saadaan homma toimimaan
      Plantti suurinPlantti = null;
      Plantti apuPlantti = null;
      Plantti apuPlantti2 = null;
      Klimppi klimppiEka = null;
      Plantti planttiEka = null;
      //lista johon talletetaan käydyt paikat
      OmaLista paikkaLista = new OmaLista();
      // uuden värin muuttuja
      char uusiVari = ' ';
      //Otetaan listan koko talteen, ettei se muutu kesken lisääntymisen
      int listanPituus = lotkoLista.koko();
      
      boolean samaPaikka = false;
      Paikka paikkaEka = null;
      Paikka paikka1 = null;
      Paikka paikka2 = null;
      
      
      //luuppi jossa käydään kaikki lötköt läpi 
      for(int i = 0;i < listanPituus; i++){
         paikanLotkot = new OmaLista();
         boolean planttiLisaantyi = false;
         boolean klimppiLisaantyi = false;
         boolean paikkaOn = false;
         boolean kayty = false;
         if(lotkoLista.alkio(i) instanceof Klimppi){
            klimppiEka = (Klimppi)lotkoLista.alkio(i);
            paikkaEka = klimppiEka.paikka();
            paikkaOn = true;
         }
         if(lotkoLista.alkio(i) instanceof Plantti){
            planttiEka = (Plantti)lotkoLista.alkio(i);
            paikkaEka = planttiEka.paikka();
            paikkaOn = true;
         }
         if(paikkaLista != null){
            for(int j = 0; j < paikkaLista.koko();j++){
               Paikka apuPaikka = (Paikka)paikkaLista.alkio(j);
               if(paikkaEka.equals(apuPaikka)){
                  kayty = true;
               }
            }
         }
         //jos paikassa ei jo käyty niin jatketaan
         if(!kayty && paikkaOn && lotkoLista.alkio(i) != null){
            for(int j=0;j<=lotkoLista.koko();j++){

               if(lotkoLista.alkio(j) instanceof Klimppi){
                  Klimppi klimppi = (Klimppi)lotkoLista.alkio(j);
                  Paikka paikka = klimppi.paikka();
                  if(paikkaEka.equals(paikka)){
                     paikanLotkot.lisaaLoppuun(lotkoLista.alkio(j));
                  }
               }
               if(lotkoLista.alkio(j) instanceof Plantti){
                  Plantti plantti = (Plantti)lotkoLista.alkio(j);
                  Paikka paikka = plantti.paikka();
                  if(paikkaEka.equals(paikka)){
                     paikanLotkot.lisaaLoppuun(lotkoLista.alkio(j));
                  }
               }
            }
            for(int j = 0;j < paikanLotkot.koko() ;j++){
               //Jos klimppi
               if(paikanLotkot.alkio(j) instanceof Klimppi){
                  Klimppi ekaKlimppi = (Klimppi)paikanLotkot.alkio(j);
                  for(int k = 0; k < paikanLotkot.koko();k++){
                     if(paikanLotkot.alkio(k) instanceof Klimppi){
                        Klimppi tokaKlimppi = (Klimppi)paikanLotkot.alkio(k);
                        if(ekaKlimppi.equals(tokaKlimppi) && !klimppiLisaantyi && ekaKlimppi.ind() < tokaKlimppi.ind()){
                           int uusiKoko = ekaKlimppi.koko() + tokaKlimppi.koko();
                           uusiKoko = uusiKoko / 2;
                           StringBuilder alkuPerima = ekaKlimppi.perima();
                           StringBuilder loppuPerima = tokaKlimppi.perima();
                        
                           String salkuPerima = alkuPerima.substring(0,4);
                           String sloppuPerima = loppuPerima.substring(4,8);
                           String uusiPerima = salkuPerima + sloppuPerima;

                           if(ekaKlimppi.vari()=='P'){uusiVari = 'S';}
                           else if(ekaKlimppi.vari()=='S'){uusiVari = 'P';}
                           Klimppi uusiKlimppi = new Klimppi((int)uusiKoko,uusiPerima,uusiVari,paikkaEka,indeksi);
                           lotkoLista.lisaaLoppuun(uusiKlimppi);
                           indeksi++;
                           klimppiLisaantyi = true;
                        }
                     }
                  }
               }
               //Jos pläntti
               if(paikanLotkot.alkio(j) instanceof Plantti){
                  boolean samanlainen = false;
                  boolean suurin = true;
                  suurinPlantti = (Plantti)paikanLotkot.alkio(j);
                  //vertaillaan plantteja
                  for(int h = 0; h <= paikanLotkot.koko() ; h++){
                     if(paikanLotkot.alkio(h) instanceof Plantti){
                        apuPlantti = (Plantti)paikanLotkot.alkio(h);
                           
                        if(suurinPlantti.ind() != apuPlantti.ind()){
                           paikka1 = suurinPlantti.paikka();
                           paikka2 = apuPlantti.paikka();
                           if(paikka1.equals(paikka2)){
                              if(suurinPlantti.compareTo(apuPlantti)){
                                 suurinPlantti = apuPlantti;
                              }
                           }
                        }
                     }  
                  }
                  //Katsellaan onko suurempaa tai samanlaista lötköä
                  for(int h = 0; h <= paikanLotkot.koko(); h++){
                     if(paikanLotkot.alkio(h) instanceof Plantti){
                        apuPlantti2 = (Plantti)paikanLotkot.alkio(h);
                        paikka1 = suurinPlantti.paikka();
                        paikka2 = apuPlantti2.paikka();
                        if(suurinPlantti.koko() == apuPlantti2.koko() && suurinPlantti.ind() != apuPlantti2.ind() && paikka1.equals(paikka2)){
                           suurin = false;
                        }
                        if(suurinPlantti.equals(apuPlantti2) && suurinPlantti.ind() != apuPlantti2.ind() && paikka1.equals(paikka2)){
                           samanlainen = true;
                        }
                     }
                  }
                  // jos paikassa ei ollut suurempaa tai samanlaista
                  if(!samanlainen && suurin && !planttiLisaantyi){
                     Plantti uusiPlantti = new Plantti(suurinPlantti, indeksi);
                     lotkoLista.lisaaLoppuun(uusiPlantti);
                     indeksi++;
                     planttiLisaantyi = true;
                  }
               }
            }
            
            paikkaLista.lisaaLoppuun(paikkaEka);
         }
      }   
   }
   
   /**Metodi joka tallentaa lotkon ennalta määrättyyn tiedostoon
     */
   public static void tallenna(){
      try{
         File tiedosto = new File("lotkot.txt");
         FileOutputStream tulostusvirta = new FileOutputStream(tiedosto);
         PrintWriter kirjoittaja = new PrintWriter(tulostusvirta, true);
         kirjoittaja.println(lotkoLista.alkio(0));
         for(int i = 0 ; i <= lotkoLista.koko(); i++){
            if(lotkoLista.alkio(i) != null){
               //tulostetaan klimppi  
               if(lotkoLista.alkio(i) instanceof Klimppi){
                  Klimppi klimppi = (Klimppi)lotkoLista.alkio(i);
                  kirjoittaja.println(klimppiTallennus(klimppi));
 
               }
               //tulostetaan plantti
               if(lotkoLista.alkio(i) instanceof Plantti){
                  Plantti plantti = (Plantti)lotkoLista.alkio(i);
                  kirjoittaja.println(planttiTallennus(plantti));
               
               }
            }
         }
      }
      catch(FileNotFoundException e){
         System.out.println(VIRHE);
      }
   }
   /**Metodi joka täytää stringin väleillä haluttuun määtään sakka
   *@param s String mihin lisätään välejä
   *@param m Kuinka pitkä string halutaan
   *@return uusi täytetty string 
   */
   public static String valeja(String s, int m){
     return String.format("%1$-" + m + "s", s);  
   }
   
   /**Metodi joka karsii Lotkosta paikan ja indeksin tallennusta varten
     *@param klimppi tallennettava klimppi
     *@return karsittu klimppi
     */
   public static String klimppiTallennus(Klimppi klimppi){
      
      String koko = Integer.toString(klimppi.koko());
      String perima = (klimppi.perima()).toString();
      String vari = klimppi.vari() + "";
       
      String klimppiUlos = "Klimppi "+ EROTIN+valeja(koko,8)+EROTIN+valeja(perima,8)+EROTIN+valeja(vari,8)+EROTIN;
      return klimppiUlos;
   }
   /**Metodi joka karsii Lotkosta paikan ja indeksin tallennusta varten
     *@param plantti tallennettava plantti
     *@return karsittu plantti
     */
   public static String planttiTallennus(Plantti plantti){

      String koko = Integer.toString(plantti.koko());
      String perima = (plantti.perima()).toString();
      String soikea = Boolean.toString(plantti.soikea());
      
      String planttiUlos = "Plantti "+EROTIN+valeja(koko,8)+EROTIN+valeja(perima,8)+EROTIN+valeja(soikea,8)+EROTIN;
      return planttiUlos;
   }
}
