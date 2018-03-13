package mulakat;

import java.awt.Color;
import java.util.ListIterator;
//import java.awt.geom.Line2D;
import java.util.Random;
import java.util.Vector;

import javax.swing.JButton;

public class mulakat {
public static thread mainFrame;
public static mulakat nesne;

public static int[][] santranc_tahta;
public static int[][] komsuluk_matrisi;
public static double[][] Q_matrisi;
public static int[] baslangic_durumu;
public static int baslangic1;
public static int durum;
public static int hamle;
public static int sonraki_hamle;
public static int sonraki_durum;

public mulakat(){
	santranc_tahta=new int[8][8];
	for(int i=0;i<8;i++){
		for(int j=0;j<8;j++){
			santranc_tahta[i][j]=0;
		}
	}
	
	komsuluk_matrisi=new int[64][64];
	for(int i=0;i<64;i++){
		for(int j=0;j<64;j++){
			 komsuluk_matrisi[i][j]=-1;
		}
	}
	komsuluk_matrisi[7][7]=100;//hedef duðumun kendine dönüþ ödülü 100 olarak belirlendi.
	siyah_pul_yerlestir();
	siyahpul_sil();
	komsuluk_matrisi_olustur();
	Q_matrisi=new double[64][64];
	for(int i=0;i<64;i++){
		for(int j=0;j<64;j++){
			Q_matrisi[i][j]=0;
		}
	}
	
	baslangic_durumu=new int[64];
	for(int i=0;i<64;i++){
		baslangic_durumu[i]=i;
	}
	
	Q_learning();
	santranc_matris_yaz();
	adimlari_yaz(7*8+0,0*8+7);
}

public void adimlari_yaz(int baslangic,int hedef){
	double max=0;
	int hamle1=0;
	 System.out.print("("+(baslangic/8)+","+(baslangic%8)+")->");
	
	while(baslangic!=hedef){
		int durum=baslangic;
		for(int j=0;j<64;j++){
			
			if(max<Q_matrisi[durum][j]&&durum!=j){//dugumun kendine donusu olmasýn
			  max=Q_matrisi[durum][j];
			  hamle1=j;
			  
			}
		}
		
		if(max>0){
			//durum-hamle düðümü arasinda yol çiz
			
			 System.out.print("("+(hamle1/8)+","+(hamle1%8)+")");
			
			 
			 /**
			  * dugumun komsuluklarýnda siyah pul varsa konumlarýný öðren!!!
			  * (karþýlaþýlan siyah pullarýn konumu)
			  * 
			  * */

		}
		baslangic=hamle1;
		if(baslangic!=hedef) System.out.print("->");
	}
}

public void Q_learning(){
	
	
	for(int j=0;j<3000;j++){
		//System.out.println(j+".iterasyon..............");
		baslangic1=new Random().nextInt(64);
		baslangic1=baslangic_durumu[baslangic1];
		durum=baslangic1;
		//while(basla){
			
			 do{
				 hamle=new Random().nextInt(64);
			 }while(komsuluk_matrisi[durum][hamle]==-1);
			// hamle=a.max_Q(durum,dosya);
			 sonraki_durum=hamle;
			 sonraki_hamle=Q_max(sonraki_durum);
			Q_matrisi[durum][hamle]=komsuluk_matrisi[durum][hamle]+(0.8)*Q_matrisi[sonraki_durum][sonraki_hamle];
			// System.out.println("durum("+durum+")->hamle("+hamle+") kazanc="+Q_matrisi[durum][hamle]);
			while(durum!=(8*0+7) ){
				durum=sonraki_durum;
				hamle=sonraki_hamle;
				sonraki_durum=hamle;
				sonraki_hamle=Q_max(hamle);
			  if(hamle==(8*0+7)){
				  sonraki_hamle=(8*0+7);
				//  basla=false;
			  }
				Q_matrisi[durum][hamle]=komsuluk_matrisi[durum][hamle]+(0.8)*Q_matrisi[sonraki_durum][sonraki_hamle];
			  // 	System.out.println("durum("+durum+")->hamle("+hamle+") kazanc="+Q_matrisi[durum][hamle]);
			
			   	
			}
	//	}
		 
			
		//System.out.println("..........................................");

	}
	
}

public int Q_max(int durum){
	
	 double max=0;
	   int hamle1=-2;//komsuluk_matrisinde olmayan bir deðer olduðu için bu rakam verildi
	    int sayac=0;
	    int[] rastgele=new int[64];
	    Vector<Integer> x=new Vector<Integer>();
	   for(int i=0;i<64;i++){
		   if(komsuluk_matrisi[durum][i]>=0){ //komsuluk_matrisi[][] komsuluk varsa 0 veya 100 döner.
			   
			   if(max<=Q_matrisi[durum][i]&&Q_matrisi[durum][i]!=0){
				   
				   if(max<Q_matrisi[durum][i]){
					x.clear();
 					x.addElement(new Integer(i));
 					hamle1=i;
 					max=Q_matrisi[durum][i];
				   }
				   else if(max==Q_matrisi[durum][i]){
					   x.addElement(new Integer(i));
    				   hamle1=i;
				   }
			   
			   }
			  
			  else if(Q_matrisi[durum][i]==0){
			      rastgele[sayac]=i;
			      sayac++;
		   }
			    
		   }
		   
		   
		  
	   }
	   
	   if(hamle1==-2){
		   int sayi = (int)(Math.random()*sayac);//0-sayac arasinda random sayi üretir.
		   return rastgele[sayi];
	   }
	   
	   else{
		   int sayi1 = (int)(Math.random()*x.size());//0-vektor elaman sayisi arasinda random sayi üretir.
		   return ((Integer)x.get(sayi1)).intValue();
		   }
}
public void komsuluk_bul(int dugum_numarasi){
	int konum_x=dugum_numarasi/8+1;//i+1
	int konum_y=dugum_numarasi%8+1;//j+1 
	System.out.println("komsuluklar:");
	for(int sayac1=0;sayac1<3;sayac1++){
		for(int sayac2=0;sayac2<3;sayac2++){
			if(konum_x>=0&&konum_x<8&&konum_y>=0&&konum_y<8){
				int komsu_dugum=konum_x*8+konum_y;
				
				if(!pul_var_mi(konum_x,konum_y)&&dugum_numarasi!=komsu_dugum){
					System.out.println("("+konum_x+","+konum_y+")");
					if(komsu_dugum!=7) komsuluk_matrisi[dugum_numarasi][komsu_dugum]=0;
					else komsuluk_matrisi[dugum_numarasi][komsu_dugum]=100;//hedef dugum olan (0,7)'ye geçiþ ödülü 100dür.
				}
				
			}
			konum_y--;
		}
		konum_x--;
		konum_y=dugum_numarasi%8+1;//konum_y ilk deðerine döndürüldü.
	}
}

public void komsuluk_matrisi_olustur(){
	for(int i=0;i<64;i++){
		komsuluk_bul(i);
	}
}
public void siyah_pul_yerlestir(){
	int kac_tane=0;
	Random rastgele=new Random();
	kac_tane=rastgele.nextInt(7)+3;
	System.out.println("random:"+kac_tane);
	int konum_x = 0,konum_y = 0;
	while(kac_tane>0){
	do{
		konum_x=rastgele.nextInt(8);
		konum_y=rastgele.nextInt(8);
	}while((konum_x==7&&konum_y==0)||(konum_x==0&&konum_y==7) );
	santranc_tahta[konum_x][konum_y]=-1;
	System.out.println("x:"+konum_x+"  y:"+konum_y);
	kac_tane--;
	}
}


public void siyahpul_sil(){
	/**
	 * oyunun baþlamadan bitmemesi için gerekli bir önlemdir.
	 * beyaz puulun baþlangýcý olan (7,0) ve oyun bitiþi sayýlan konum (0,7)
	 * noktalarýndan bir çýkýþ ve giriþ kapýsýnýn açýk olmasý gerekir.
	 * */
	Random rastgele=new Random();
	if(pul_var_mi(6,0)&&pul_var_mi(6,1)&&pul_var_mi(7,1)){
		int secim=rastgele.nextInt(3);
		if(secim==0) santranc_tahta[6][0]=0;
		else if(secim==1) santranc_tahta[6][1]=0;
		else santranc_tahta[7][1]=0;
	}
	 
	else if(pul_var_mi(0,6)&&pul_var_mi(1,6)&&pul_var_mi(1,7)){
		int secim=rastgele.nextInt(3);
		if(secim==0) santranc_tahta[0][6]=0;
		else if(secim==1) santranc_tahta[1][6]=0;
		else santranc_tahta[1][7]=0;
	}
}
public boolean pul_var_mi(int i,int j){
	if(santranc_tahta[i][j]==-1) return true;
	else return false;
}

public void komsu_matris_yaz(){
	System.out.println("komsuluk_matrisi:");
	for(int i=0;i<64;i++){
		for(int j=0;j<64;j++){
			System.out.print(komsuluk_matrisi[i][j]+"  ");
		}
		System.out.println();
	}
}

public void  santranc_matris_yaz(){
	System.out.println("santranc_tahta:");

	for(int i=0;i<8;i++){
		for(int j=0;j<8;j++){
			System.out.print(santranc_tahta[i][j]+"  ");
		}
		System.out.println();
	}
}

public void Q_matris_yaz(){
	for(int i=0;i<64;i++){
		for(int j=0;j<64;j++){
			System.out.print(Q_matrisi[i][j]+"  ");
		}
		System.out.println();
	}
}

	public static void main(String[] args) {
		 nesne=new mulakat();
		 //nesne.Q_learning();
		//nesne.Q_matris_yaz();
		
		 mainFrame = new thread(nesne);
		 mainFrame.setSize(900,600);
		
		 mainFrame.setTitle("OYUN");
		 mainFrame.setBackground(Color.blue);
		 mainFrame.setResizable(false);
		 mainFrame.setVisible(true);
		 
		//nesne.santranc_matris_yaz();
		//nesne.adimlari_yaz(7*8+0,0*8+7);
		 System.out.println();
		nesne.Q_matris_yaz();
	}

}
