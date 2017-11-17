package database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class VeritabaniIslemleri {

    private Connection con = null;
    String veritabaniUrl;
    String kullaniciAdi;
    String sifre;
    String[] tabloIsimleri = {"yorum", "girdi", "kullanicilar", "blog"};

    public VeritabaniIslemleri() {
        this.veritabaniUrl = "jdbc:mysql://localhost:3306/blogdatabase?zeroDateTimeBehavior=convertToNull";
        this.kullaniciAdi = "root";
        this.sifre = "rasyona";
    }

    public VeritabaniIslemleri(String url,String kullaniciAdi, String sifre) {
        this.veritabaniUrl = url;
        this.kullaniciAdi = kullaniciAdi;
        this.sifre = sifre;
    }

    public void baglan() throws Exception {
        //Eger baglanti varsa true gonder
        if ((con != null)) {
            if (con.isClosed() == false) {
                return;
            }
        }
        //Baglanti yoksa yeniden olustur
        Class.forName("com.mysql.jdbc.Driver");
        con = (Connection) DriverManager.getConnection(this.veritabaniUrl, this.kullaniciAdi, this.sifre);
    }

    public void baglantiyiKes() throws SQLException {
        if (con != null) {
            if (!con.isClosed()) {
                con.close();
            }
        }
    }

    public void tumTablolariTemizle() throws SQLException {
        for (String s : tabloIsimleri) {
            tabloIceriginiSil(s);
        }
    }

    public void tabloIceriginiSil(String tabloAdi) throws SQLException {
        String sorgu = "DELETE FROM " + tabloAdi;
        Statement stmt = null;
        stmt = con.createStatement();
        stmt.executeUpdate(sorgu);
        stmt.close();
    }

    public boolean kullaniciOlustur(Kullanici k) throws SQLException {
        String sorgu = "INSERT INTO kullanicilar VALUES (DEFAULT,?,?,?,?,?)";
        PreparedStatement pstmt = con.prepareStatement(sorgu);
        pstmt.setString(1, k.getKullaniciEmail());
        pstmt.setString(2, k.getKullaniciSifre());
        pstmt.setString(3, k.getKullaniciAdSoyad());
        pstmt.setString(4, k.getKullaniciIzin());
        pstmt.setTimestamp(5, k.getKayitTarih());
        int sonuc = pstmt.executeUpdate();
        pstmt.close();
        return sonuc > 0;
    }
    
     public void kullanicilariListedenEkle(List<Kullanici> kullaniciListesi) throws SQLException{
         String sorgu = "INSERT INTO kullanicilar VALUES (DEFAULT,?,?,?,?,?)";
         PreparedStatement pstmt  = null;
         try {
            con.setAutoCommit(false);
            pstmt = con.prepareStatement(sorgu);
            for(Kullanici k: kullaniciListesi){
                pstmt.setString(1, k.getKullaniciEmail());
                pstmt.setString(2, k.getKullaniciSifre());
                pstmt.setString(3, k.getKullaniciAdSoyad());
                pstmt.setString(4, k.getKullaniciIzin());
                pstmt.setTimestamp(5, k.getKayitTarih());
                pstmt.executeUpdate();
            }
            con.commit();
            con.setAutoCommit(true);
            pstmt.close();
        } catch (SQLException ex) {
            con.rollback();
            throw ex;
        }
     }



    public boolean blogOlustur(Blog b) throws SQLException {
        String sorgu = "INSERT INTO blog VALUES (DEFAULT,?,?,?,?)";
        PreparedStatement pstmt = con.prepareStatement(sorgu);
        pstmt.setInt(1, b.getKullaniciID());
        pstmt.setString(2, b.getBlogBaslik());
        pstmt.setString(3, b.getAciklama());
        pstmt.setTimestamp(4, b.getOlusturmaTarih());
        int sonuc = pstmt.executeUpdate();
        pstmt.close();
        return sonuc > 0;

    }

    public boolean girdiOlustur(Girdi g) throws SQLException {
        String sorgu = "INSERT INTO girdi VALUES (DEFAULT,?,?,?,?,?)";
        PreparedStatement pstmt = con.prepareStatement(sorgu);
        pstmt.setInt(1, g.getBlogID());
        pstmt.setInt(2, g.getKullaniciID());
        pstmt.setString(3, g.getGirdiBaslik());
        pstmt.setString(4, g.getGirdiIcerik());
        pstmt.setTimestamp(5, g.getGirdiTarih());
        int sonuc = pstmt.executeUpdate();
        pstmt.close();
        return sonuc > 0;
    }

    public boolean yorumOlustur(Yorum y) throws SQLException {
        String sorgu = "INSERT INTO yorum VALUES (DEFAULT,?,?,?,?,?)";
        PreparedStatement pstmt = con.prepareStatement(sorgu);
        pstmt.setInt(1, y.getGirdiID());
        pstmt.setInt(2, y.getKullaniciID());
        pstmt.setString(3, y.getYorumBaslik());
        pstmt.setString(4, y.getYorumIcerik());
        pstmt.setTimestamp(5, y.getYorumTarih());
        int sonuc = pstmt.executeUpdate();
        pstmt.close();
        return sonuc > 0;
    }

    public boolean emailKayitliMi(String email) throws SQLException {
        String sorgu = "SELECT KullaniciEmail FROM kullanicilar where kullaniciEmail = ?";
        PreparedStatement pstmt = con.prepareStatement(sorgu);
        pstmt.setString(1, email);
        // Gelen ResultSet bos degilse excecute fonksiyonu true dondurur.
        boolean sonuc = pstmt.execute();
        pstmt.close();
        return sonuc;
    }

    public int kullaniciIDsiniBul(String kullaniciEmail) throws SQLException {
        String sorgu = "SELECT KullaniciID FROM kullanicilar where kullaniciEmail = ?";
        PreparedStatement pstmt = con.prepareStatement(sorgu);
        pstmt.setString(1, kullaniciEmail);
        ResultSet rs = pstmt.executeQuery();

        int sonuc = 0;
        if(rs.next())
            sonuc  = rs.getInt("KullaniciID");

        pstmt.close();
        rs.close();

        return sonuc;

    }

    public int blogIDSiniBul(Timestamp OlusturmaTarih) throws SQLException {
        String sorgu = "SELECT BlogID FROM blog where OlusturmaTarih = ?";
        PreparedStatement pstmt = con.prepareStatement(sorgu);
        pstmt.setTimestamp(1, OlusturmaTarih);
        ResultSet rs = pstmt.executeQuery();
        int sonuc = 0;
        if(rs.next())
            sonuc  = rs.getInt("BlogID");


        pstmt.close();
        rs.close();

        return sonuc;
      }

    public int girdiIDSiniBul(Timestamp GirdiTarih) throws SQLException {
        String sorgu = "SELECT GirdiID FROM girdi where GirdiTarih = ?";
        PreparedStatement pstmt = con.prepareStatement(sorgu);
        pstmt.setTimestamp(1, GirdiTarih);
        ResultSet rs = pstmt.executeQuery();
        int sonuc = 0;
        if(rs.next())
            sonuc  = rs.getInt("GirdiID");


        pstmt.close();
        rs.close();

        return sonuc;
   }

    public Kullanici kullaniciBilgisiniGetir(String kullaniciEmail,
            String kullaniciSifre) throws SQLException {

        String sorgu = "SELECT * FROM kullanicilar where "
                + "kullaniciEmail = ? and kullaniciSifre = ?";

        PreparedStatement pstmt = con.prepareStatement(sorgu);
        pstmt.setString(1, kullaniciEmail);
        pstmt.setString(2, kullaniciSifre);
        ResultSet rs = pstmt.executeQuery();
        Kullanici k = null;
        if (rs.next()) {
            k = new Kullanici();
            k.setKullaniciID(rs.getInt("KullaniciID"));
            k.setKullaniciEmail(rs.getString("KullaniciEmail"));
            k.setKullaniciSifre(rs.getString("KullaniciSifre"));
            k.setKullaniciAdSoyad(rs.getString("KullaniciAdSoyad"));
            k.setKullaniciIzin(rs.getString("KullaniciIzin"));
            k.setKayitTarih(rs.getTimestamp("KayitTarih"));
        }
        pstmt.close();
        rs.close();
        return k;
    }

    public Kullanici kullaniciBilgisiGetir(String kullaniciEmail) throws SQLException {
        String sorgu = "SELECT * FROM kullanicilar where kullaniciEmail = ?";
        PreparedStatement pstmt = con.prepareStatement(sorgu);
        pstmt.setString(1, kullaniciEmail);
        ResultSet rs = pstmt.executeQuery();
        Kullanici k = null;
        if (rs.next()) {
            k = new Kullanici();
            k.setKullaniciID(rs.getInt("KullaniciID"));
            k.setKullaniciEmail(rs.getString("KullaniciEmail"));
            k.setKullaniciSifre(rs.getString("KullaniciSifre"));
            k.setKullaniciAdSoyad(rs.getString("KullaniciAdSoyad"));
            k.setKullaniciIzin(rs.getString("KullaniciIzin"));
            k.setKayitTarih(rs.getTimestamp("KayitTarih"));
        }
        pstmt.close();
        rs.close();
        return k;
    }

    public Kullanici kullaniciBilgisiGetir(int kullaniciID) throws SQLException {
        String sorgu = "SELECT * FROM kullanicilar where kullaniciID = ?";
        PreparedStatement pstmt = con.prepareStatement(sorgu);
        pstmt.setInt(1, kullaniciID);
        ResultSet rs = pstmt.executeQuery();
        Kullanici k = null;
        if (rs.next()) {
            k = new Kullanici();
            k.setKullaniciID(rs.getInt("KullaniciID"));
            k.setKullaniciEmail(rs.getString("KullaniciEmail"));
            k.setKullaniciSifre(rs.getString("KullaniciSifre"));
            k.setKullaniciAdSoyad(rs.getString("KullaniciAdSoyad"));
            k.setKullaniciIzin(rs.getString("KullaniciIzin"));
            k.setKayitTarih(rs.getTimestamp("KayitTarih"));
        }
        pstmt.close();
        rs.close();
        return k;
    }

    public List<Kullanici> tumKullanicilariGetir() throws SQLException{

        String sorgu = "SELECT * FROM Kullanicilar";
        PreparedStatement pstmt = con.prepareStatement(sorgu);
        ResultSet rs = pstmt.executeQuery();
        List <Kullanici> kullaniciList = null;
        if(rs.next()){
            kullaniciList = new ArrayList<Kullanici>();
        }
        rs.beforeFirst();
        while (rs.next()) {
            Kullanici k = new Kullanici();
            k.setKullaniciID(rs.getInt("KullaniciID"));
            k.setKullaniciEmail(rs.getString("KullaniciEmail"));
            k.setKullaniciSifre(rs.getString("KullaniciSifre"));
            k.setKullaniciAdSoyad(rs.getString("KullaniciAdSoyad"));
            k.setKullaniciIzin(rs.getString("KullaniciIzin"));
            k.setKayitTarih(rs.getTimestamp("KayitTarih"));
            kullaniciList.add(k);
        }
        pstmt.close();
        rs.close();
        return kullaniciList;
    }

    public Blog blogBilgisiGetir(int blogID) throws SQLException{

        String sorgu = "SELECT * FROM Blog where BlogID = ?";
        PreparedStatement pstmt = con.prepareStatement(sorgu);
        pstmt.setInt(1, blogID);
        ResultSet rs = pstmt.executeQuery();
        Blog b = null;
        if (rs.next()) {
            b = new Blog();
            b.setBlogID(rs.getInt("BlogID"));
            b.setKullaniciID(rs.getInt("KullaniciID"));
            b.setBlogBaslik(rs.getString("BlogBaslik"));
            b.setAciklama(rs.getString("Aciklama"));
            b.setOlusturmaTarih(rs.getTimestamp("OlusturmaTarih"));
        }
        pstmt.close();
        rs.close();
        return b;

    }

    public int[] blogIstatistikGetir(int blogID) throws SQLException{
        int[] res = new int[2];
        String sorgu = "SELECT count(*) FROM Girdi where BlogID = ?";
        PreparedStatement pstmt = con.prepareStatement(sorgu);
        pstmt.setInt(1, blogID);
        ResultSet rs = pstmt.executeQuery();
        if(rs.next()){
            res[0] = rs.getInt(1);
        }

        sorgu = "select count(*) from yorum y WHERE " +
                "y.GirdiID in" +
                "(Select g.GirdiId FROM Girdi g where g.BlogID = ? )";

        pstmt = con.prepareStatement(sorgu);
        pstmt.setInt(1, blogID);
        rs = pstmt.executeQuery();
        if(rs.next()){
            res[1] = rs.getInt(1);
        }

        pstmt.close();
        rs.close();
        return res;
    }

    public List<Blog> tumBloglariGetir() throws SQLException{

        String sorgu = "SELECT * FROM Blog";
        PreparedStatement pstmt = con.prepareStatement(sorgu);
        ResultSet rs = pstmt.executeQuery();
        List <Blog> blogList = null;
        if(rs.next()){
            blogList = new ArrayList<Blog>();
        }
        rs.beforeFirst();
        while (rs.next()) {
            Blog b = new Blog();
            b.setBlogID(rs.getInt("BlogID"));
            b.setKullaniciID(rs.getInt("KullaniciID"));
            b.setBlogBaslik(rs.getString("BlogBaslik"));
            b.setAciklama(rs.getString("Aciklama"));
            b.setOlusturmaTarih(rs.getTimestamp("OlusturmaTarih"));
            blogList.add(b);
        }
        pstmt.close();
        rs.close();
        return blogList;
    }

    public List<Blog> birKullaniciyaAitTumBloglariGetir(Kullanici k)
            throws SQLException
    {

        String sorgu = "SELECT * FROM Blog WHERE KullaniciID = ?";
        PreparedStatement pstmt = con.prepareStatement(sorgu);
        pstmt.setInt(1, k.getKullaniciID());
        ResultSet rs = pstmt.executeQuery();
        List <Blog> blogList = null;
        if(rs.next()){
            blogList = new ArrayList<Blog>();
        }
        rs.beforeFirst();
        while (rs.next()) {
            Blog b = new Blog();
            b.setBlogID(rs.getInt("BlogID"));
            b.setKullaniciID(rs.getInt("KullaniciID"));
            b.setBlogBaslik(rs.getString("BlogBaslik"));
            b.setAciklama(rs.getString("Aciklama"));
            b.setOlusturmaTarih(rs.getTimestamp("OlusturmaTarih"));
            blogList.add(b);
        }
        pstmt.close();
        rs.close();
        return blogList;

    }

    public List<Girdi> birBlogaAitTumGirdileriGetir(Blog b)
            throws SQLException
    {
        String sorgu = "SELECT * FROM Girdi WHERE BlogID = ?";
        PreparedStatement pstmt = con.prepareStatement(sorgu);
        pstmt.setInt(1, b.getBlogID());
        ResultSet rs = pstmt.executeQuery();
        List <Girdi> girdiList = null;
        if(rs.next()){
            girdiList = new ArrayList<Girdi>();
        }
        rs.beforeFirst();

        while(rs.next()) {
            Girdi g = new Girdi();
            g.setGirdiID(rs.getInt("GirdiID"));
            g.setBlogID(rs.getInt("BlogID"));
            g.setKullaniciID(rs.getInt("KullaniciID"));
            g.setGirdiBaslik(rs.getString("GirdiBaslik"));
            g.setGirdiIcerik(rs.getString("GirdiIcerik"));
            g.setGirdiTarih(rs.getTimestamp("GirdiTarih"));
            girdiList.add(g);
        }
        pstmt.close();
        rs.close();
        return girdiList;

    }

    public List<Yorum> birGirdiyeAitTumYorumlariGetir(Girdi g)
            throws SQLException
    {
        String sorgu = "SELECT * FROM Yorum WHERE GirdiID = ?";
        PreparedStatement pstmt = con.prepareStatement(sorgu);
        pstmt.setInt(1, g.getGirdiID());
        ResultSet rs = pstmt.executeQuery();
        List <Yorum> yorumList = null;
        if(rs.next()){
            yorumList = new ArrayList<Yorum>();
        }
        rs.beforeFirst();

        while (rs.next()) {
            Yorum y = new Yorum();
            y.setYorumID(rs.getInt("YorumID"));
            y.setGirdiID(rs.getInt("GirdiID"));
            y.setKullaniciID(rs.getInt("KullaniciID"));
            y.setYorumBaslik(rs.getString("YorumBaslik"));
            y.setYorumIcerik(rs.getString("YorumIcerik"));
            y.setYorumTarih(rs.getTimestamp("YorumTarih"));
            yorumList.add(y);
        }
        pstmt.close();
        rs.close();
        return yorumList;

    }

    public boolean yorumuSil(Yorum  y) throws SQLException{
        String sorgu = "DELETE FROM Yorum WHERE YorumID = ?";
        PreparedStatement pstmt = con.prepareStatement(sorgu);
        pstmt = con.prepareStatement(sorgu);
        pstmt.setInt(1, y.getYorumID());
        boolean sonuc = (pstmt.executeUpdate()>0);
        pstmt.close();
        return sonuc;
    }

    public boolean girdiyiSil(Girdi g) throws SQLException{
        String sorgu = "DELETE FROM Girdi WHERE GirdiID = ?";
        PreparedStatement pstmt = con.prepareStatement(sorgu);
        pstmt = con.prepareStatement(sorgu);
        pstmt.setInt(1, g.getGirdiID());
        boolean sonuc = (pstmt.executeUpdate()>0);
        pstmt.close();
        return sonuc;
    }

    public boolean bloguSil(Blog b) throws SQLException{
        String sorgu = "DELETE FROM Blog WHERE BlogID = ?";
        PreparedStatement pstmt = con.prepareStatement(sorgu);
        pstmt = con.prepareStatement(sorgu);
        pstmt.setInt(1, b.getBlogID());
        boolean sonuc = (pstmt.executeUpdate()>0);
        pstmt.close();
        return sonuc;
    }

    public boolean kullaniciyiSil(Kullanici k) throws SQLException{
        String sorgu = "DELETE FROM Kullanicilar WHERE KullaniciID = ?";
        PreparedStatement pstmt = con.prepareStatement(sorgu);
        pstmt = con.prepareStatement(sorgu);
        pstmt.setInt(1, k.getKullaniciID());
        boolean sonuc = (pstmt.executeUpdate()>0);
        pstmt.close();

        return sonuc;
    }

    public boolean blogBilgisiGuncelle(Blog b) throws SQLException{
        String sorgu = "UPDATE blog SET BlogBaslik = ?, " +
                "Aciklama = ?, KullaniciId = ? WHERE BlogID = ?";
        PreparedStatement pstmt = con.prepareStatement(sorgu);
        pstmt.setString(1, b.getBlogBaslik());
        pstmt.setString(2, b.getAciklama());
        pstmt.setInt(3, b.getKullaniciID());
        pstmt.setInt(4, b.getBlogID());
        int sonuc = pstmt.executeUpdate();
        pstmt.close();

        return (sonuc > 0);

    }

    public boolean kullaniciBilgisiGuncelle(Kullanici k) throws SQLException{
        String sorgu = "UPDATE kullanicilar SET KullaniciEmail = ?, " +
                "KullaniciSifre = ?, KullaniciAdSoyad = ?, " +
                "KullaniciIzin = ? WHERE KullaniciID = ?";
        PreparedStatement pstmt = con.prepareStatement(sorgu);
        pstmt.setString(1, k.getKullaniciEmail());
        pstmt.setString(2, k.getKullaniciSifre());
        pstmt.setString(3, k.getKullaniciAdSoyad());
        pstmt.setString(4, k.getKullaniciIzin());
        pstmt.setInt(5, k.getKullaniciID());
        int sonuc = pstmt.executeUpdate();
        pstmt.close();
        return (sonuc > 0);

    }

    public String [] tabloKolonAdlariniGetir(String tabloAdi) throws SQLException{
        String sorgu = "SELECT * FROM " + tabloAdi;
        PreparedStatement pstmt = con.prepareStatement(sorgu);
        ResultSetMetaData metaData = pstmt.getMetaData();

        int sayi = metaData.getColumnCount();
        String[] sonuc = new String[sayi];
        for(int i = 0; i<sayi; i++)
            sonuc[i] = metaData.getColumnName(i+1);

        pstmt.close();
        return sonuc;
    }

}