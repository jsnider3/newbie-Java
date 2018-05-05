/**
 * Girl Genius is a webcomic by Phil Foglio about a lost princess.
 *
 * @author: Josh Snider
 */

package com.joshuasnider.workspace.comicgetter;

import java.io.File;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;

public class GirlGeniusImageGetter extends ComicGetter {

  public static String home = "http://www.girlgeniusonline.com";
  public static String title = home + "/ggmain/strips/ggmain";

  public static void main(String[] args) {
    new GirlGeniusImageGetter().getAll();
  }

  /**
   * Try to find the link to the double page if the given comic is one.
   * If it isn't or we can't find it, return null.
   * FIXME: This is a performance issue.
   */
  public String getDoublePage(String url) {
    String link = null;
    try {
      Document doc = Jsoup.connect(url).get();
      Elements els = doc.select("a[href*=doublespreads]");
      if (els.size() > 0) {
        link = home + els.get(0).attr("href");
        link = link.substring(0, link.lastIndexOf('.')) + ".jpg";
      }
    } catch (Exception e) {
      System.err.println(url + " failed");
    }
    return link;
  }

  public String getName() {
    return "GirlGenius";
  }

  public String[] getToFrom(String index) {
    String[] tofrom = new String[2];
    tofrom[0] = title + index + ".jpg";
    tofrom[1] = getDir() + index + ".jpg";
    /*String doublePage = getDoublePage(
      "http://www.girlgeniusonline.com/comic.php?date=" + index);
    if (doublePage != null) {
      tofrom[0] = doublePage;
    }*/
    return tofrom;
  }

  private class ComicIterator implements Iterator<String> {

    private String index = "20021104";

    @Override
    public boolean hasNext() {
      return index.compareTo(getToday("yyyyMMdd")) <= 0;
    }

    @Override
    public String next() {
      String ret = index;
      try {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new SimpleDateFormat("yyyyMMdd").parse(index));
        switch (cal.get(Calendar.DAY_OF_WEEK)) {
          case Calendar.FRIDAY:
            index = getNextDay(index, "yyyyMMdd");
          case Calendar.MONDAY:
          case Calendar.WEDNESDAY:
          case Calendar.SATURDAY:
            index = getNextDay(index, "yyyyMMdd");
          case Calendar.TUESDAY:
          case Calendar.THURSDAY:
          case Calendar.SUNDAY:
            index = getNextDay(index, "yyyyMMdd");
            break;
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      return ret;
    }

  }

  public Iterator<String> iterator() {
    return new ComicIterator();
  }

}
