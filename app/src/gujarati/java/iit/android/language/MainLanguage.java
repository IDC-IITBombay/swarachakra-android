package iit.android.language;

import iit.android.language.Language;
import iit.android.swarachakra.KeyAttr;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;

public class MainLanguage extends Language {

	public MainLanguage() {
		//Log.d("debug","gujarati");
		name = "gujarati";
		symbol = "\u0A97\u0AC1";
		nKeys = 136;
		myKey = new ArrayList<KeyAttr>(nKeys);
		defaultChakra = new String[] { "\u0ACD", "\u0ABE", "\u0ABF", "\u0AC0",
				"\u0AC1", "\u0AC2", "\u0AC7", "\u0AC8", "\u0ACB", "\u0ACC" };
		halantExists = true;
		halantEnd = 35;

		KeyAttr tempKey = new KeyAttr();
		for (int i = 0; i < nKeys; i++) {
			myKey.add(i, tempKey);
		}

		KeyAttr myKey0 = new KeyAttr();
		myKey0.code = 1;
		myKey0.label = "\u0A95";//ka
		myKey0.showChakra = true ;
		myKey0.showCustomChakra = false ;
		myKey0.customChakraLayout = new String[] {} ;
		myKey0.showIcon = false ;
		myKey0.icon = "";
		myKey0.changeLayout = false ;
		myKey0.layout = "";
		myKey.set(0, myKey0);

		KeyAttr myKey1 = new KeyAttr();
		myKey1.code = 2;
		myKey1.label = "\u0A96";//kha
		myKey1.showChakra = true ;
		myKey1.showCustomChakra = false ;
		myKey1.customChakraLayout = new String[] {} ;
		myKey1.showIcon = false ;
		myKey1.icon = "";
		myKey1.changeLayout = false ;
		myKey1.layout = "";
		myKey.set(1, myKey1);

		KeyAttr myKey2 = new KeyAttr();
		myKey2.code = 3;
		myKey2.label = "\u0A97";//ga
		myKey2.showChakra = true ;
		myKey2.showCustomChakra = false ;
		myKey2.customChakraLayout = new String[] {} ;
		myKey2.showIcon = false ;
		myKey2.icon = "";
		myKey2.changeLayout = false ;
		myKey2.layout = "";
		myKey.set(2, myKey2);

		KeyAttr myKey3 = new KeyAttr();
		myKey3.code = 4;
		myKey3.label = "\u0A98";//gha
		myKey3.showChakra = true ;
		myKey3.showCustomChakra = false ;
		myKey3.customChakraLayout = new String[] {} ;
		myKey3.showIcon = false ;
		myKey3.icon = "";
		myKey3.changeLayout = false ;
		myKey3.layout = "";
		myKey.set(3, myKey3);

		KeyAttr myKey4 = new KeyAttr();
		myKey4.code = 5;
		myKey4.label = "\u0A99";//gna
		myKey4.showChakra = true ;
		myKey4.showCustomChakra = false ;
		myKey4.customChakraLayout = new String[] {} ;
		myKey4.showIcon = false ;
		myKey4.icon = "";
		myKey4.changeLayout = false ;
		myKey4.layout = "";
		myKey.set(4, myKey4);

		KeyAttr myKey5 = new KeyAttr();
		myKey5.code = 6;
		myKey5.label = "\u0A9A";//cha
		myKey5.showChakra = true ;
		myKey5.showCustomChakra = false ;
		myKey5.customChakraLayout = new String[] {} ;
		myKey5.showIcon = false ;
		myKey5.icon = "";
		myKey5.changeLayout = false ;
		myKey5.layout = "";
		myKey.set(5, myKey5);

		KeyAttr myKey6 = new KeyAttr();
		myKey6.code = 7;
		myKey6.label = "\u0A9B";//chha
		myKey6.showChakra = true ;
		myKey6.showCustomChakra = false ;
		myKey6.customChakraLayout = new String[] {} ;
		myKey6.showIcon = false ;
		myKey6.icon = "";
		myKey6.changeLayout = false ;
		myKey6.layout = "";
		myKey.set(6, myKey6);

		KeyAttr myKey7 = new KeyAttr();
		myKey7.code = 8;
		myKey7.label = "\u0A9C";//ja
		myKey7.showChakra = true ;
		myKey7.showCustomChakra = false ;
		myKey7.customChakraLayout = new String[] {} ;
		myKey7.showIcon = false ;
		myKey7.icon = "";
		myKey7.changeLayout = false ;
		myKey7.layout = "";
		myKey.set(7, myKey7);

		KeyAttr myKey8 = new KeyAttr();
		myKey8.code = 9;
		myKey8.label = "\u0A9D";//jha
		myKey8.showChakra = true ;
		myKey8.showCustomChakra = false ;
		myKey8.customChakraLayout = new String[] {} ;
		myKey8.showIcon = false ;
		myKey8.icon = "";
		myKey8.changeLayout = false ;
		myKey8.layout = "";
		myKey.set(8, myKey8);

		KeyAttr myKey9 = new KeyAttr();
		myKey9.code = 10;
		myKey9.label = "\u0A9E";//jna
		myKey9.showChakra = true ;
		myKey9.showCustomChakra = false ;
		myKey9.customChakraLayout = new String[] {} ;
		myKey9.showIcon = false ;
		myKey9.icon = "";
		myKey9.changeLayout = false ;
		myKey9.layout = "";
		myKey.set(9, myKey9);

		KeyAttr myKey10 = new KeyAttr();
		myKey10.code = 11;
		myKey10.label = "\u0A9F";//tta
		myKey10.showChakra = true ;
		myKey10.showCustomChakra = false ;
		myKey10.customChakraLayout = new String[] {} ;
		myKey10.showIcon = false ;
		myKey10.icon = "";
		myKey10.changeLayout = false ;
		myKey10.layout = "";
		myKey.set(10, myKey10);

		KeyAttr myKey11 = new KeyAttr();
		myKey11.code = 12;
		myKey11.label = "\u0AA0" ;//ttha
		myKey11.showChakra = true ;
		myKey11.showCustomChakra = false ;
		myKey11.customChakraLayout = new String[] {} ;
		myKey11.showIcon = false ;
		myKey11.icon = "";
		myKey11.changeLayout = false ;
		myKey11.layout = "";
		myKey.set(11, myKey11);

		KeyAttr myKey12 = new KeyAttr();
		myKey12.code = 13;
		myKey12.label = "\u0AA1";//dda
		myKey12.showChakra = true ;
		myKey12.showCustomChakra = false ;
		myKey12.customChakraLayout = new String[] {} ;
		myKey12.showIcon = false ;
		myKey12.icon = "";
		myKey12.changeLayout = false ;
		myKey12.layout = "";
		myKey.set(12, myKey12);

		KeyAttr myKey13 = new KeyAttr();
		myKey13.code = 14;
		myKey13.label = "\u0AA2";//ddha
		myKey13.showChakra = true ;
		myKey13.showCustomChakra = false ;
		myKey13.customChakraLayout = new String[] {} ;
		myKey13.showIcon = false ;
		myKey13.icon = "";
		myKey13.changeLayout = false;
		myKey13.layout = "";
		myKey.set(13, myKey13);

		KeyAttr myKey14 = new KeyAttr();
		myKey14.code = 15;
		myKey14.label = "\u0AA3";//nna
		myKey14.showChakra = true ;
		myKey14.showCustomChakra = false ;
		myKey14.customChakraLayout = new String[] {} ;
		myKey14.showIcon = false ;
		myKey14.icon = "";
		myKey14.changeLayout = false ;
		myKey14.layout = "";
		myKey.set(14, myKey14);

		KeyAttr myKey15 = new KeyAttr();
		myKey15.code = 16;
		myKey15.label = "\u0AA4";//ta
		myKey15.showChakra = true ;
		myKey15.showCustomChakra = false ;
		myKey15.customChakraLayout = new String[] {} ;
		myKey15.showIcon = false ;
		myKey15.icon = "";
		myKey15.changeLayout = false ;
		myKey15.layout = "";
		myKey.set(15, myKey15);

		KeyAttr myKey16 = new KeyAttr();
		myKey16.code = 17;
		myKey16.label = "\u0AA5";//tha
		myKey16.showChakra = true ;
		myKey16.showCustomChakra = false ;
		myKey16.customChakraLayout = new String[] {} ;
		myKey16.showIcon = false ;
		myKey16.icon = "";
		myKey16.changeLayout = false ;
		myKey16.layout = "";
		myKey.set(16, myKey16);

		KeyAttr myKey17 = new KeyAttr();
		myKey17.code = 18;
		myKey17.label = "\u0AA6";//da
		myKey17.showChakra = true ;
		myKey17.showCustomChakra = false ;
		myKey17.customChakraLayout = new String[] {} ;
		myKey17.showIcon = false ;
		myKey17.icon = "";
		myKey17.changeLayout = false ;
		myKey17.layout = "";
		myKey.set(17, myKey17);

		KeyAttr myKey18 = new KeyAttr();
		myKey18.code = 19;
		myKey18.label = "\u0AA7";//dha
		myKey18.showChakra = true ;
		myKey18.showCustomChakra = false ;
		myKey18.customChakraLayout = new String[] {} ;
		myKey18.showIcon = false ;
		myKey18.icon = "";
		myKey18.changeLayout = false ;
		myKey18.layout = "";
		myKey.set(18, myKey18);

		KeyAttr myKey19 = new KeyAttr();
		myKey19.code = 20;
		myKey19.label = "\u0AA8"; //na
		myKey19.showChakra = true ;
		myKey19.showCustomChakra = false ;
		myKey19.customChakraLayout = new String[] {} ;
		myKey19.showIcon = false ;
		myKey19.icon = "";
		myKey19.changeLayout = false ;
		myKey19.layout = "";
		myKey.set(19, myKey19);

		KeyAttr myKey20 = new KeyAttr();
		myKey20.code = 21;
		myKey20.label = "\u0AAA";//pa
		myKey20.showChakra = true ;
		myKey20.showCustomChakra = false ;
		myKey20.customChakraLayout = new String[] {} ;
		myKey20.showIcon = false ;
		myKey20.icon = "";
		myKey20.changeLayout = false ;
		myKey20.layout = "";
		myKey.set(20, myKey20);

		KeyAttr myKey21 = new KeyAttr();
		myKey21.code = 22;
		myKey21.label = "\u0AAB";//pha
		myKey21.showChakra = true ;
		myKey21.showCustomChakra = false ;
		myKey21.customChakraLayout = new String[] {} ;
		myKey21.showIcon = false ;
		myKey21.icon = "";
		myKey21.changeLayout = false ;
		myKey21.layout = "";
		myKey.set(21, myKey21);

		KeyAttr myKey22 = new KeyAttr();
		myKey22.code = 23;
		myKey22.label = "\u0AAC";//ba
		myKey22.showChakra = true ;
		myKey22.showCustomChakra = false ;
		myKey22.customChakraLayout = new String[] {} ;
		myKey22.showIcon = false ;
		myKey22.icon = "";
		myKey22.changeLayout = false ;
		myKey22.layout = "";
		myKey.set(22, myKey22);

		KeyAttr myKey23 = new KeyAttr();
		myKey23.code = 24;
		myKey23.label = "\u0AAD";//bha
		myKey23.showChakra = true ;
		myKey23.showCustomChakra = false ;
		myKey23.customChakraLayout = new String[] {} ;
		myKey23.showIcon = false ;
		myKey23.icon = "";
		myKey23.changeLayout = false ;
		myKey23.layout = "";
		myKey.set(23, myKey23);

		KeyAttr myKey24 = new KeyAttr();
		myKey24.code = 25;
		myKey24.label = "\u0AAE";//ma
		myKey24.showChakra = true ;
		myKey24.showCustomChakra = false ;
		myKey24.customChakraLayout = new String[] {} ;
		myKey24.showIcon = false ;
		myKey24.icon = "";
		myKey24.changeLayout = false ;
		myKey24.layout = "";
		myKey.set(24, myKey24);

		KeyAttr myKey25 = new KeyAttr();
		myKey25.code = 26;
		myKey25.label = "\u0AAF";//ya
		myKey25.showChakra = true ;
		myKey25.showCustomChakra = false ;
		myKey25.customChakraLayout = new String[] {} ;
		myKey25.showIcon = false ;
		myKey25.icon = "";
		myKey25.changeLayout = false ;
		myKey25.layout = "";
		myKey.set(25, myKey25);

		KeyAttr myKey26 = new KeyAttr();
		myKey26.code = 27;
		myKey26.label = "\u0AB0";//ra
		myKey26.showChakra = true ;
		myKey26.showCustomChakra = false ;
		myKey26.customChakraLayout = new String[] {} ;
		myKey26.showIcon = false ;
		myKey26.icon = "";
		myKey26.changeLayout = false ;
		myKey26.layout = "";
		myKey.set(26, myKey26);

		KeyAttr myKey27 = new KeyAttr();
		myKey27.code = 28;
		myKey27.label = "\u0AB2";//la
		myKey27.showChakra = true ;
		myKey27.showCustomChakra = false ;
		myKey27.customChakraLayout = new String[] {} ;
		myKey27.showIcon = false ;
		myKey27.icon = "";
		myKey27.changeLayout = false ;
		myKey27.layout = "";
		myKey.set(27, myKey27);

		KeyAttr myKey28 = new KeyAttr();
		myKey28.code = 29;
		myKey28.label = "\u0AB3"; //lla
		myKey28.showChakra = true ;
		myKey28.showCustomChakra = false ;
		myKey28.customChakraLayout = new String[] {} ;
		myKey28.showIcon = false ;
		myKey28.icon = "";
		myKey28.changeLayout = false ;
		myKey28.layout = "";
		myKey.set(28, myKey28);

		KeyAttr myKey29 = new KeyAttr();
		myKey29.code = 30;
		myKey29.label = "\u0AB5";//va
		myKey29.showChakra = true ;
		myKey29.showCustomChakra = false ;
		myKey29.customChakraLayout = new String[] {} ;
		myKey29.showIcon = false ;
		myKey29.icon = "";
		myKey29.changeLayout = false ;
		myKey29.layout = "";
		myKey.set(29, myKey29);

		KeyAttr myKey30 = new KeyAttr();
		myKey30.code = 31;
		myKey30.label = "\u0AB6";//sha
		myKey30.showChakra = true ;
		myKey30.showCustomChakra = false ;
		myKey30.customChakraLayout = new String[] {} ;
		myKey30.showIcon = false ;
		myKey30.icon = "";
		myKey30.changeLayout = false ;
		myKey30.layout = "";
		myKey.set(30, myKey30);

		KeyAttr myKey31 = new KeyAttr();
		myKey31.code = 32;
		myKey31.label = "\u0AB7";//ssha
		myKey31.showChakra = true ;
		myKey31.showCustomChakra = false ;
		myKey31.customChakraLayout = new String[] {} ;
		myKey31.showIcon = false ;
		myKey31.icon = "";
		myKey31.changeLayout = false ;
		myKey31.layout = "";
		myKey.set(31, myKey31);

		KeyAttr myKey32 = new KeyAttr();
		myKey32.code = 33;
		myKey32.label = "\u0AB8";//sa
		myKey32.showChakra = true ;
		myKey32.showCustomChakra = false ;
		myKey32.customChakraLayout = new String[] {} ;
		myKey32.showIcon = false ;
		myKey32.icon = "";
		myKey32.changeLayout = false ;
		myKey32.layout = "";
		myKey.set(32, myKey32);

		KeyAttr myKey33 = new KeyAttr();
		myKey33.code = 34;
		myKey33.label = "\u0AB9"; //ha
		myKey33.showChakra = true ;
		myKey33.showCustomChakra = false ;
		myKey33.customChakraLayout = new String[] {} ;
		myKey33.showIcon = false ;
		myKey33.icon = "";
		myKey33.changeLayout = false ;
		myKey33.layout = "";
		myKey.set(33, myKey33);

		KeyAttr myKey34 = new KeyAttr();
		myKey34.code = 35;
		myKey34.label = "\u0A95\u0ACD\u0AB7";//ksha
		myKey34.showChakra = true ;
		myKey34.showCustomChakra = false ;
		myKey34.customChakraLayout = new String[] {} ;
		myKey34.showIcon = false ;
		myKey34.icon = "";
		myKey34.changeLayout = false ;
		myKey34.layout = "";
		myKey.set(34, myKey34);

		KeyAttr myKey35 = new KeyAttr();
		myKey35.code = 36;
		myKey35.label = "\u0A9C\u0ACD\u0A9E";//jna
		myKey35.showChakra = true ;
		myKey35.showCustomChakra = false ;
		myKey35.customChakraLayout = new String[] {} ;
		myKey35.showIcon = false ;
		myKey35.icon = "";
		myKey35.changeLayout = false ;
		myKey35.layout = "";
		myKey.set(35, myKey35);

		KeyAttr myKey36 = new KeyAttr();
		myKey36.code = 37;
		myKey36.label = "\u0AB6\u0ACD\u0AB0";//shra
		myKey36.showChakra = true ;
		myKey36.showCustomChakra = false ;
		myKey36.customChakraLayout = new String[] {} ;
		myKey36.showIcon = false ;
		myKey36.icon = "";
		myKey36.changeLayout = false ;
		myKey36.layout = "";
		myKey.set(36, myKey36);

		KeyAttr myKey37 = new KeyAttr();
		myKey37.code = 38;
		myKey37.label = "\u0AAA\u0ACD\u0AB0";//pra
		myKey37.showChakra = true ;
		myKey37.showCustomChakra = false ;
		myKey37.customChakraLayout = new String[] { };
		myKey37.showIcon = false ;
		myKey37.icon = "";
		myKey37.changeLayout = false ;
		myKey37.layout = "";
		myKey.set(37, myKey37);

		KeyAttr myKey38 = new KeyAttr();
		myKey38.code = 39;
		myKey38.label = "\u0AA4"+"\u0ACD"+"\u0AB0";//tra
		myKey38.showChakra = true ;
		myKey38.showCustomChakra = false ;
		myKey38.customChakraLayout = new String[] {};
		myKey38.showIcon = false ;
		myKey38.icon = "";
		myKey38.changeLayout = false ;
		myKey38.layout = "";
		myKey.set(38, myKey38);

		KeyAttr myKey39 = new KeyAttr();
		myKey39.code = 40;
		myKey39.label = "\u0AE0";//ru
		myKey39.showChakra = false ;
		myKey39.showCustomChakra = false ;
		myKey39.customChakraLayout = new String[] {};
		myKey39.showIcon = false ;
		myKey39.icon = "";
		myKey39.changeLayout = false ;
		myKey39.layout = "";
		myKey.set(39, myKey39);

		KeyAttr myKey40 = new KeyAttr();
		myKey40.code = 41;
		myKey40.label = "\u002E" ;//gujarati fullstop
		myKey40.showChakra = false ;
		myKey40.showCustomChakra = false ;
		myKey40.customChakraLayout = new String[] {} ;
		myKey40.showIcon = false ;
		myKey40.icon = "";
		myKey40.changeLayout = false ;
		myKey40.layout = "";
		myKey.set(40, myKey40);

		KeyAttr myKey41 = new KeyAttr();
		myKey41.code = 42;
		myKey41.label = "\u002C" ;//comma
		myKey41.showChakra = false ;
		myKey41.showCustomChakra = false ;
		myKey41.customChakraLayout = new String[] {} ;
		myKey41.showIcon = false ;
		myKey41.icon = "";
		myKey41.changeLayout = false ;
		myKey41.layout = "";
		myKey.set(41, myKey41);

		KeyAttr myKey42 = new KeyAttr();
		myKey42.code = 43;
		myKey42.label = "\u0021" ;//exclamation mark
		myKey42.showChakra = false ;
		myKey42.showCustomChakra = false ;
		myKey42.customChakraLayout = new String[] {} ;
		myKey42.showIcon = false ;
		myKey42.icon = "";
		myKey42.changeLayout = false ;
		myKey42.layout = "";
		myKey.set(42, myKey42);

		KeyAttr myKey43 = new KeyAttr();
		myKey43.code = 44;
		myKey43.label = "\u003F" ;//question mark
		myKey43.showChakra = false ;
		myKey43.showCustomChakra = false ;
		myKey43.customChakraLayout = new String[] {} ;
		myKey43.showIcon = false ;
		myKey43.icon = "";
		myKey43.changeLayout = false ;
		myKey43.layout = "";
		myKey.set(43, myKey43);

		KeyAttr myKey44 = new KeyAttr();
		myKey44.code = 45;
		myKey44.label = "\u2014"; //hyphen to make a break in the flow of a sentence
		myKey44.showChakra = false ;
		myKey44.showCustomChakra = false ;
		myKey44.customChakraLayout = new String[] {} ;
		myKey44.showIcon = false ;
		myKey44.icon = "";
		myKey44.changeLayout = false ;
		myKey44.layout = "";
		myKey.set(44, myKey44);

		KeyAttr myKey45 = new KeyAttr();
		myKey45.code = 46;
		myKey45.label = "\"";//Double quote
		myKey45.showChakra = false ;
		myKey45.showCustomChakra = false ;
		myKey45.customChakraLayout = new String[] {} ;
		myKey45.showIcon = false ;
		myKey45.icon = "";
		myKey45.changeLayout = false ;
		myKey45.layout = "";
		myKey.set(45, myKey45);

		KeyAttr myKey46 = new KeyAttr();
		myKey46.code = 47;
		myKey46.label = "'";//single quote
		myKey46.showChakra = false ;
		myKey46.showCustomChakra = false ;
		myKey46.customChakraLayout = new String[] {} ;
		myKey46.showIcon = false ;
		myKey46.icon = "";
		myKey46.changeLayout = false ;
		myKey46.layout = "";
		myKey.set(46, myKey46);

		KeyAttr myKey47 = new KeyAttr();
		myKey47.code = 48;
		myKey47.label = "\u0A82";//anuswar
		myKey47.showChakra = false ;
		myKey47.showCustomChakra = false ;
		myKey47.customChakraLayout = new String[] {} ;
		myKey47.showIcon = false;
		myKey47.icon = "";
		myKey47.changeLayout = false ;
		myKey47.layout = "";
		myKey.set(47, myKey47);

		KeyAttr myKey48 = new KeyAttr();
		myKey48.code = 49;
		myKey48.label = "\u0AC5"; //a ki matra as in captain
		myKey48.showChakra = false ;
		myKey48.showCustomChakra = false ;
		myKey48.customChakraLayout = new String[] {} ;
		myKey48.showIcon = false;
		myKey48.icon = "";
		myKey48.changeLayout = false ;
		myKey48.layout = "";
		myKey.set(48, myKey48);

		KeyAttr myKey49 = new KeyAttr();
		myKey49.code = 50;
		myKey49.label = "\u0A83";//visarga
		myKey49.showChakra = false ;
		myKey49.showCustomChakra = false ;
		myKey49.customChakraLayout = new String[] {} ;
		myKey49.showIcon = false;
		myKey49.icon = "";
		myKey49.changeLayout = false ;
		myKey49.layout = "";
		myKey.set(49, myKey49);

		KeyAttr myKey50 = new KeyAttr();
		myKey50.code = 51;
		myKey50.label = "";//not sure--earlier varyacha ra
		myKey50.showChakra = false ;
		myKey50.showCustomChakra = false ;
		myKey50.customChakraLayout = new String[] {} ;
		myKey50.showIcon = false ;
		myKey50.icon = "";
		myKey50.changeLayout = false ;
		myKey50.layout = "";
		myKey.set(50, myKey50);

		KeyAttr myKey51 = new KeyAttr();
		myKey51.code = 52;
		myKey51.label = "" ;//rashtra chinha
		myKey51.showChakra = false ;
		myKey51.showCustomChakra = false ;
		myKey51.customChakraLayout = new String[] {} ;
		myKey51.showIcon = true ;
		myKey51.icon = "rakar";
		myKey51.changeLayout = false ;
		myKey51.layout = "";
		myKey51.isException = true;
		myKey.set(51, myKey51);

		KeyAttr myKey52 = new KeyAttr();
		myKey52.code = 53;
		myKey52.label = "" ;//rafar
		myKey52.showChakra = false ;
		myKey52.showCustomChakra = false ;
		myKey52.customChakraLayout = new String[] {} ;
		myKey52.showIcon = true ;
		myKey52.icon = "rafar";
		myKey52.changeLayout = false ;
		myKey52.layout = "";
		myKey52.isException = true;
		myKey.set(52, myKey52);


		KeyAttr myKey53 = new KeyAttr();
		myKey53.code = 54;
		myKey53.label = "." ;//don't know, not used in Marathi
		myKey53.showChakra = false ;
		myKey53.showCustomChakra = false ;
		myKey53.customChakraLayout = new String[] {} ;
		myKey53.showIcon = false ;
		myKey53.icon = "";
		myKey53.changeLayout = false ;
		myKey53.layout = "";
		myKey.set(53, myKey53);

		KeyAttr myKey54 = new KeyAttr();
		myKey54.code = 55;
		myKey54.label = "\u0AF0" ;//devanagari abbreviation sign
		myKey54.showChakra = false ;
		myKey54.showCustomChakra = false ;
		myKey54.customChakraLayout = new String[] {} ;
		myKey54.showIcon = false ;
		myKey54.icon = "";
		myKey54.changeLayout = false ;
		myKey54.layout = "";
		myKey.set(54, myKey54);

		KeyAttr myKey55 = new KeyAttr();
		myKey55.code = 56;
		myKey55.label = "\u0AE7" ; //Gujarati digit one
		myKey55.showChakra = false ;
		myKey55.showCustomChakra = false ;
		myKey55.customChakraLayout = new String[] {} ;
		myKey55.showIcon = false ;
		myKey55.icon = "";
		myKey55.changeLayout = false ;
		myKey55.layout = "";
		myKey.set(55, myKey55);

		KeyAttr myKey56 = new KeyAttr();
		myKey56.code = 57;
		myKey56.label = "\u0AE8" ; //gujarati digit two
		myKey56.showChakra = false ;
		myKey56.showCustomChakra = false ;
		myKey56.customChakraLayout = new String[] {} ;
		myKey56.showIcon = false ;
		myKey56.icon = "";
		myKey56.changeLayout = false ;
		myKey56.layout = "";
		myKey.set(56, myKey56);

		KeyAttr myKey57 = new KeyAttr();
		myKey57.code = 58;
		myKey57.label = "\u0AE9" ; //devanagari digit three
		myKey57.showChakra = false ;
		myKey57.showCustomChakra = false ;
		myKey57.customChakraLayout = new String[] {} ;
		myKey57.showIcon = false ;
		myKey57.icon = "";
		myKey57.changeLayout = false ;
		myKey57.layout = "";
		myKey.set(57, myKey57);

		KeyAttr myKey58 = new KeyAttr();
		myKey58.code = 59;
		myKey58.label = "\u002B" ; //not sure what
		myKey58.showChakra = false ;
		myKey58.showCustomChakra = false ;
		myKey58.customChakraLayout = new String[] {} ;
		myKey58.showIcon = false ;
		myKey58.icon = "";
		myKey58.changeLayout = false ;
		myKey58.layout = "";
		myKey.set(58, myKey58);

		KeyAttr myKey59 = new KeyAttr();
		myKey59.code = 60;
		myKey59.label = "," ;//not sure, perhaps English comma
		myKey59.showChakra = false ;
		myKey59.showCustomChakra = false ;
		myKey59.customChakraLayout = new String[] {} ;
		myKey59.showIcon = false ;
		myKey59.icon = "";
		myKey59.changeLayout = false ;
		myKey59.layout = "";
		myKey.set(59, myKey59);

		KeyAttr myKey60 = new KeyAttr();
		myKey60.code = 61;
		myKey60.label = "\u0965" ; //devanagari double danda
		myKey60.showChakra = false ;
		myKey60.showCustomChakra = false ;
		myKey60.customChakraLayout = new String[] {} ;
		myKey60.showIcon = false ;
		myKey60.icon = "";
		myKey60.changeLayout = false ;
		myKey60.layout = "";
		myKey.set(60, myKey60);

		KeyAttr myKey61 = new KeyAttr();
		myKey61.code = 62;
		myKey61.label = "\u0AEA" ; //devanagari digit four
		myKey61.showChakra = false ;
		myKey61.showCustomChakra = false ;
		myKey61.customChakraLayout = new String[] {} ;
		myKey61.showIcon = false ;
		myKey61.icon = "";
		myKey61.changeLayout = false ;
		myKey61.layout = "";
		myKey.set(61, myKey61);

		KeyAttr myKey62 = new KeyAttr();
		myKey62.code = 63;
		myKey62.label = "\u0AEB" ; //devanagari digit five
		myKey62.showChakra = false ;
		myKey62.showCustomChakra = false ;
		myKey62.customChakraLayout = new String[] {} ;
		myKey62.showIcon = false ;
		myKey62.icon = "";
		myKey62.changeLayout = false ;
		myKey62.layout = "";
		myKey.set(62, myKey62);

		KeyAttr myKey63 = new KeyAttr();
		myKey63.code = 64;
		myKey63.label = "\u0AEC" ; //devanagari digit six
		myKey63.showChakra = false ;
		myKey63.showCustomChakra = false ;
		myKey63.customChakraLayout = new String[] {} ;
		myKey63.showIcon = false ;
		myKey63.icon = "";
		myKey63.changeLayout = false ;
		myKey63.layout = "";
		myKey.set(63, myKey63);

		KeyAttr myKey64 = new KeyAttr();
		myKey64.code = 65;
		myKey64.label = "\u002D" ; //not sure
		myKey64.showChakra = false ;
		myKey64.showCustomChakra = false ;
		myKey64.customChakraLayout = new String[] {} ;
		myKey64.showIcon = false ;
		myKey64.icon = "";
		myKey64.changeLayout = false ;
		myKey64.layout = "";
		myKey.set(64, myKey64);

		KeyAttr myKey65 = new KeyAttr();
		myKey65.code = 66;
		myKey65.label = ";" ; //semicolon
		myKey65.showChakra = false ;
		myKey65.showCustomChakra = false ;
		myKey65.customChakraLayout = new String[] {} ;
		myKey65.showIcon = false ;
		myKey65.icon = "";
		myKey65.changeLayout = false ;
		myKey65.layout = "";
		myKey.set(65, myKey65);

		KeyAttr myKey66 = new KeyAttr();
		myKey66.code = 67;
		myKey66.label = "_" ; //underscore
		myKey66.showChakra = false ;
		myKey66.showCustomChakra = false ;
		myKey66.customChakraLayout = new String[] {} ;
		myKey66.showIcon = false ;
		myKey66.icon = "";
		myKey66.changeLayout = false ;
		myKey66.layout = "";
		myKey.set(66, myKey66);

		KeyAttr myKey67 = new KeyAttr();
		myKey67.code = 68;
		myKey67.label = "\u0AED" ; //devanagari digit seven
		myKey67.showChakra = false ;
		myKey67.showCustomChakra = false ;
		myKey67.customChakraLayout = new String[] {} ;
		myKey67.showIcon = false ;
		myKey67.icon = "";
		myKey67.changeLayout = false ;
		myKey67.layout = "";
		myKey.set(67, myKey67);

		KeyAttr myKey68 = new KeyAttr();
		myKey68.code = 69;
		myKey68.label = "\u0AEE" ; //devanagari digit eight
		myKey68.showChakra = false ;
		myKey68.showCustomChakra = false ;
		myKey68.customChakraLayout = new String[] {} ;
		myKey68.showIcon = false ;
		myKey68.icon = "";
		myKey68.changeLayout = false ;
		myKey68.layout = "";
		myKey.set(68, myKey68);

		KeyAttr myKey69 = new KeyAttr();
		myKey69.code = 70;
		myKey69.label = "\u0AEF" ; //devanagari digit nine
		myKey69.showChakra = false ;
		myKey69.showCustomChakra = false ;
		myKey69.customChakraLayout = new String[] {} ;
		myKey69.showIcon = false ;
		myKey69.icon = "";
		myKey69.changeLayout = false ;
		myKey69.layout = "";
		myKey.set(69, myKey69);

		KeyAttr myKey70 = new KeyAttr();
		myKey70.code = 71;
		myKey70.label = "x" ; //not sure, perhaps multiplication
		myKey70.showChakra = false ;
		myKey70.showCustomChakra = false ;
		myKey70.customChakraLayout = new String[] {} ;
		myKey70.showIcon = false ;
		myKey70.icon = "";
		myKey70.changeLayout = false ;
		myKey70.layout = "";
		myKey.set(70, myKey70);

		KeyAttr myKey71 = new KeyAttr();
		myKey71.code = 72;
		myKey71.label = "=" ; //equal to
		myKey71.showChakra = false ;
		myKey71.showCustomChakra = false ;
		myKey71.customChakraLayout = new String[] {} ;
		myKey71.showIcon = false ;
		myKey71.icon = "";
		myKey71.changeLayout = false ;
		myKey71.layout = "";
		myKey.set(71, myKey71);

		KeyAttr myKey72 = new KeyAttr();
		myKey72.code = 73;
		myKey72.label = ":" ;//colon
		myKey72.showChakra = false ;
		myKey72.showCustomChakra = false ;
		myKey72.customChakraLayout = new String[] {} ;
		myKey72.showIcon = false ;
		myKey72.icon = "";
		myKey72.changeLayout = false ;
		myKey72.layout = "";
		myKey.set(72, myKey72);

		KeyAttr myKey73 = new KeyAttr();
		myKey73.code = 74;
		myKey73.label = "\u2014" ; //not sure
		myKey73.showChakra = false ;
		myKey73.showCustomChakra = false ;
		myKey73.customChakraLayout = new String[] {} ;
		myKey73.showIcon = false ;
		myKey73.icon = "";
		myKey73.changeLayout = false ;
		myKey73.layout = "";
		myKey.set(73, myKey73);

		KeyAttr myKey74 = new KeyAttr();
		myKey74.code = 75;
		myKey74.label = "*" ;//asterisk
		myKey74.showChakra = false ;
		myKey74.showCustomChakra = false ;
		myKey74.customChakraLayout = new String[] {} ;
		myKey74.showIcon = false ;
		myKey74.icon = "";
		myKey74.changeLayout = false ;
		myKey74.layout = "";
		myKey.set(74, myKey74);

		KeyAttr myKey75 = new KeyAttr();
		myKey75.code = 76;
		myKey75.label = "\u0AE6" ;//devanagari digit zero
		myKey75.showChakra = false ;
		myKey75.showCustomChakra = false ;
		myKey75.customChakraLayout = new String[] {} ;
		myKey75.showIcon = false ;
		myKey75.icon = "";
		myKey75.changeLayout = false ;
		myKey75.layout = "";
		myKey.set(75, myKey75);

		KeyAttr myKey76 = new KeyAttr();
		myKey76.code = 77;
		myKey76.label = "#" ;//hash
		myKey76.showChakra = false ;
		myKey76.showCustomChakra = false ;
		myKey76.customChakraLayout = new String[] {} ;
		myKey76.showIcon = false ;
		myKey76.icon = "";
		myKey76.changeLayout = false ;
		myKey76.layout = "";
		myKey.set(76, myKey76);

		KeyAttr myKey77 = new KeyAttr();
		myKey77.code = 78;
		myKey77.label = "\u00F7" ;//not sure
		myKey77.showChakra = false ;
		myKey77.showCustomChakra = false ;
		myKey77.customChakraLayout = new String[] {} ;
		myKey77.showIcon = false ;
		myKey77.icon = "";
		myKey77.changeLayout = false ;
		myKey77.layout = "";
		myKey.set(77, myKey77);

		KeyAttr myKey78 = new KeyAttr();
		myKey78.code = 79;
		myKey78.label = "|" ;//not sure, perhaps pipe char
		myKey78.showChakra = false ;
		myKey78.showCustomChakra = false ;
		myKey78.customChakraLayout = new String[] {} ;
		myKey78.showIcon = false ;
		myKey78.icon = "";
		myKey78.changeLayout = false ;
		myKey78.layout = "";
		myKey.set(78, myKey78);

		KeyAttr myKey79 = new KeyAttr();
		myKey79.code = 80;
		myKey79.label = "\u20B9" ; //not sure
		myKey79.showChakra = false ;
		myKey79.showCustomChakra = false ;
		myKey79.customChakraLayout = new String[] {} ;
		myKey79.showIcon = false ;
		myKey79.icon = "";
		myKey79.changeLayout = false ;
		myKey79.layout = "";
		myKey.set(79, myKey79);

		KeyAttr myKey80 = new KeyAttr();
		myKey80.code = 81;
		myKey80.label = "$" ; //dollar sign
		myKey80.showChakra = true ;
		myKey80.showCustomChakra = true ;
		myKey80.customChakraLayout = new String[] { "\u09F3","\u0BF9","\u20AC","\u20A4","\u00A2" ,"\u00A3","\u00A5", "\u20B0","\uFDFC","\u20BD","\u0E3F"} ;//various currencies
		myKey80.showIcon = false ;
		myKey80.icon = "";
		myKey80.changeLayout = false ;
		myKey80.layout = "";
		myKey.set(80, myKey80);

		KeyAttr myKey81 = new KeyAttr();
		myKey81.code = 82;
		myKey81.label = "%" ;//pecent sign
		myKey81.showChakra = false ;
		myKey81.showCustomChakra = false ;
		myKey81.customChakraLayout = new String[] {} ;
		myKey81.showIcon = false ;
		myKey81.icon = "";
		myKey81.changeLayout = false ;
		myKey81.layout = "";
		myKey.set(81, myKey81);

		KeyAttr myKey82 = new KeyAttr();
		myKey82.code = 83;
		myKey82.label = "\u2122" ;//not sure
		myKey82.showChakra = false ;
		myKey82.showCustomChakra = false ;
		myKey82.customChakraLayout = new String[] {} ;
		myKey82.showIcon = false ;
		myKey82.icon = "";
		myKey82.changeLayout = false ;
		myKey82.layout = "";
		myKey.set(82, myKey82);

		KeyAttr myKey83 = new KeyAttr();
		myKey83.code = 84;
		myKey83.label = "\u00A9" ;//not sure
		myKey83.showChakra = false ;
		myKey83.showCustomChakra = false ;
		myKey83.customChakraLayout = new String[] {} ;
		myKey83.showIcon = false ;
		myKey83.icon = "";
		myKey83.changeLayout = false ;
		myKey83.layout = "";
		myKey.set(83, myKey83);

		KeyAttr myKey84 = new KeyAttr();
		myKey84.code = 85;
		myKey84.label = "/" ;//forward slash
		myKey84.showChakra = false ;
		myKey84.showCustomChakra = false ;
		myKey84.customChakraLayout = new String[] {} ;
		myKey84.showIcon = false ;
		myKey84.icon = "";
		myKey84.changeLayout = false ;
		myKey84.layout = "";
		myKey.set(84, myKey84);

		KeyAttr myKey85 = new KeyAttr();
		myKey85.code = 86;
		myKey85.label = "\\" ;//backslash
		myKey85.showChakra = false ;
		myKey85.showCustomChakra = false ;
		myKey85.customChakraLayout = new String[] {} ;
		myKey85.showIcon = false ;
		myKey85.icon = "";
		myKey85.changeLayout = false ;
		myKey85.layout = "";
		myKey.set(85, myKey85);

		KeyAttr myKey86 = new KeyAttr();
		myKey86.code = 87;
		myKey86.label = "@" ;//at the rate sign
		myKey86.showChakra = false ;
		myKey86.showCustomChakra = false ;
		myKey86.customChakraLayout = new String[] {} ;
		myKey86.showIcon = false ;
		myKey86.icon = "";
		myKey86.changeLayout = false ;
		myKey86.layout = "";
		myKey.set(86, myKey86);

		KeyAttr myKey87 = new KeyAttr();
		myKey87.code = 88;
		myKey87.label = "&" ;//ampersand
		myKey87.showChakra = false ;
		myKey87.showCustomChakra = false ;
		myKey87.customChakraLayout = new String[] {} ;
		myKey87.showIcon = false ;
		myKey87.icon = "";
		myKey87.changeLayout = false ;
		myKey87.layout = "";
		myKey.set(87, myKey87);

		KeyAttr myKey88 = new KeyAttr();
		myKey88.code = 89;
		myKey88.label = "\u0AD0" ; //om sign
		myKey88.showChakra = false ;
		myKey88.showCustomChakra = false ;
		myKey88.customChakraLayout = new String[] {} ;
		myKey88.showIcon = false ;
		myKey88.icon = "";
		myKey88.changeLayout = false ;
		myKey88.layout = "";
		myKey.set(88, myKey88);

		KeyAttr myKey89 = new KeyAttr();
		myKey89.code = 90;
		myKey89.label = "\u00AE" ;//registered sign
		myKey89.showChakra = false ;
		myKey89.showCustomChakra = false ;
		myKey89.customChakraLayout = new String[] {} ;
		myKey89.showIcon = false ;
		myKey89.icon = "";
		myKey89.changeLayout = false ;
		myKey89.layout = "";
		myKey.set(89, myKey89);

		KeyAttr myKey90 = new KeyAttr();
		myKey90.code = 91;
		myKey90.label = "~" ;//tilda
		myKey90.showChakra = false ;
		myKey90.showCustomChakra = false ;
		myKey90.customChakraLayout = new String[] {} ;
		myKey90.showIcon = false ;
		myKey90.icon = "";
		myKey90.changeLayout = false ;
		myKey90.layout = "";
		myKey.set(90, myKey90);

		KeyAttr myKey91 = new KeyAttr();
		myKey91.code = 92;
		myKey91.label = "<" ;//less than sign
		myKey91.showChakra = false ;
		myKey91.showCustomChakra = false ;
		myKey91.customChakraLayout = new String[] {} ;
		myKey91.showIcon = false ;
		myKey91.icon = "";
		myKey91.changeLayout = false ;
		myKey91.layout = "";
		myKey.set(91, myKey91);

		KeyAttr myKey92 = new KeyAttr();
		myKey92.code = 93;
		myKey92.label = ">" ;//greater than sign
		myKey92.showChakra = false ;
		myKey92.showCustomChakra = false ;
		myKey92.customChakraLayout = new String[] {} ;
		myKey92.showIcon = false ;
		myKey92.icon = "";
		myKey92.changeLayout = false ;
		myKey92.layout = "";
		myKey.set(92, myKey92);

		KeyAttr myKey93 = new KeyAttr();
		myKey93.code = 94;
		myKey93.label = "^" ;//carrot sign
		myKey93.showChakra = false ;
		myKey93.showCustomChakra = false ;
		myKey93.customChakraLayout = new String[] {} ;
		myKey93.showIcon = false ;
		myKey93.icon = "";
		myKey93.changeLayout = false ;
		myKey93.layout = "";
		myKey.set(93, myKey93);

		KeyAttr myKey94 = new KeyAttr();
		myKey94.code = 95;
		myKey94.label = "" ;//not sure earliet it was OM
		myKey94.showChakra = false ;
		myKey94.showCustomChakra = false ;
		myKey94.customChakraLayout = new String[] {} ;
		myKey94.showIcon = false ;
		myKey94.icon = "";
		myKey94.changeLayout = false ;
		myKey94.layout = "";
		myKey.set(94, myKey94);

		KeyAttr myKey95 = new KeyAttr();
		myKey95.code = 96;
		myKey95.label = "" ;//not sure
		myKey95.showChakra = false ;
		myKey95.showCustomChakra = false ;
		myKey95.customChakraLayout = new String[] {} ;
		myKey95.showIcon = false ;
		myKey95.icon = "";
		myKey95.changeLayout = false ;
		myKey95.layout = "";
		myKey.set(95, myKey95);

		KeyAttr myKey96 = new KeyAttr();
		myKey96.code = 97;
		myKey96.label = "" ;//not sure
		myKey96.showChakra = false ;
		myKey96.showCustomChakra = false ;
		myKey96.customChakraLayout = new String[] {} ;
		myKey96.showIcon = false ;
		myKey96.icon = "";
		myKey96.changeLayout = false ;
		myKey96.layout = "";
		myKey.set(96, myKey96);

		KeyAttr myKey97 = new KeyAttr();
		myKey97.code = 98;
		myKey97.label = "" ;//not sure
		myKey97.showChakra = false ;
		myKey97.showCustomChakra = false ;
		myKey97.customChakraLayout = new String[] {} ;
		myKey97.showIcon = false ;
		myKey97.icon = "";
		myKey97.changeLayout = false ;
		myKey97.layout = "";
		myKey.set(97, myKey97);

		KeyAttr myKey98 = new KeyAttr();
		myKey98.code = 99;
		myKey98.label = "(" ;//open bracket
		myKey98.showChakra = false ;
		myKey98.showCustomChakra = false ;
		myKey98.customChakraLayout = new String[] {} ;
		myKey98.showIcon = false ;
		myKey98.icon = "";
		myKey98.changeLayout = false ;
		myKey98.layout = "";
		myKey.set(98, myKey98);

		KeyAttr myKey99 = new KeyAttr();
		myKey99.code = 100;
		myKey99.label = ")" ;//close bracket
		myKey99.showChakra = false ;
		myKey99.showCustomChakra = false ;
		myKey99.customChakraLayout = new String[] {} ;
		myKey99.showIcon = false ;
		myKey99.icon = "";
		myKey99.changeLayout = false ;
		myKey99.layout = "";
		myKey.set(99, myKey99);

		KeyAttr myKey100 = new KeyAttr();
		myKey100.code = 101;
		myKey100.label = "" ;//not sure
		myKey100.showChakra = false ;
		myKey100.showCustomChakra = false ;
		myKey100.customChakraLayout = new String[] {} ;
		myKey100.showIcon = false ;
		myKey100.icon = "";
		myKey100.changeLayout = false ;
		myKey100.layout = "";
		myKey.set(100, myKey100);

		KeyAttr myKey101 = new KeyAttr();
		myKey101.code = 102;
		myKey101.label = "" ;//not sure
		myKey101.showChakra = false ;
		myKey101.showCustomChakra = false ;
		myKey101.customChakraLayout = new String[] {} ;
		myKey101.showIcon = false ;
		myKey101.icon = "";
		myKey101.changeLayout = false ;
		myKey101.layout = "";
		myKey.set(101, myKey101);

		KeyAttr myKey102 = new KeyAttr();
		myKey102.code = 103;
		myKey102.label = "" ;//not sure
		myKey102.showChakra = false ;
		myKey102.showCustomChakra = false ;
		myKey102.customChakraLayout = new String[] {} ;
		myKey102.showIcon = false ;
		myKey102.icon = "";
		myKey102.changeLayout = false ;
		myKey102.layout = "";
		myKey.set(102, myKey102);

		KeyAttr myKey103 = new KeyAttr();
		myKey103.code = 104;
		myKey103.label = "" ;//not sure
		myKey103.showChakra = false ;
		myKey103.showCustomChakra = false ;
		myKey103.customChakraLayout = new String[] {} ;
		myKey103.showIcon = false ;
		myKey103.icon = "";
		myKey103.changeLayout = false ;
		myKey103.layout = "";
		myKey.set(103, myKey103);

		KeyAttr myKey104 = new KeyAttr();
		myKey104.code = 105;
		myKey104.label = "{" ;//open curly bracket
		myKey104.showChakra = false ;
		myKey104.showCustomChakra = false ;
		myKey104.customChakraLayout = new String[] {} ;
		myKey104.showIcon = false ;
		myKey104.icon = "";
		myKey104.changeLayout = false ;
		myKey104.layout = "";
		myKey.set(104, myKey104);

		KeyAttr myKey105 = new KeyAttr();
		myKey105.code = 106;
		myKey105.label = "}" ;//close curly bracket
		myKey105.showChakra = false ;
		myKey105.showCustomChakra = false ;
		myKey105.customChakraLayout = new String[] {} ;
		myKey105.showIcon = false ;
		myKey105.icon = "";
		myKey105.changeLayout = false ;
		myKey105.layout = "";
		myKey.set(105, myKey105);

		KeyAttr myKey106 = new KeyAttr();
		myKey106 .code = 107;
		myKey106 .label = "\u0ABE" ;//kana matra velanti chakra
		myKey106 .showChakra = true ;
		myKey106 .showCustomChakra = true ;
		myKey106 .customChakraLayout = new String[] {"\u0ACD", "\u0ABE", "\u0ABF", "\u0AC0", "\u0AC1", "\u0AC2", "\u0AC7", "\u0AC8", "\u0ACB", "\u0ACC"} ;
		myKey106 .showIcon = false ;
		myKey106 .icon = "";
		myKey106 .changeLayout = false ;
		myKey106 .layout = "";
		myKey.set(106, myKey106 );

		KeyAttr myKey107  = new KeyAttr();
		myKey107.code = 108;
		myKey107.label = "\u0AC3" ;//ru chakra
		myKey107.showChakra = true ;
		myKey107.showCustomChakra = true ;
		myKey107.customChakraLayout = new String[] {"\u200C","\u0A8B","\u0AE0","\u0A8D", "\u0A91","\u200C","\u200C","\u200C","\u0AC4", "\u0AC3"} ;
		myKey107.showIcon = false ;
		myKey107.icon = "";
		myKey107.changeLayout = false ;
		myKey107.layout = "";
		myKey.set(107, myKey107);

		KeyAttr myKey108 = new KeyAttr();
		myKey108.code = 109;
		myKey108.label = "\u0A85" ;//stand alone vowel chakra
		myKey108.showChakra = true ;
		myKey108.showCustomChakra = true ;
		myKey108.customChakraLayout = new String[] {"\u0A85", "\u0A86", "\u0A87", "\u0A88", "\u0A89", "\u0A8A", "\u0A8F", "\u0A90", "\u0A93", "\u0A94"} ;
		myKey108.showIcon = false ;
		myKey108.icon = "";
		myKey108.changeLayout = false ;
		myKey108.layout = "";
		myKey.set(108, myKey108);



		KeyAttr myKey109 = new KeyAttr();
		myKey109.code = 110;
		myKey109.label = "1" ;//numeral 1
		myKey109.showChakra = false ;
		myKey109.showCustomChakra = false ;
		myKey109.customChakraLayout = new String[] {} ;
		myKey109.showIcon = false ;
		myKey109.icon = "";
		myKey109.changeLayout = false ;
		myKey109.layout = "";
		myKey.set(109, myKey109);


		KeyAttr myKey110 = new KeyAttr();
		myKey110 .code = 111;
		myKey110 .label = "2" ;//numeral 2
		myKey110 .showChakra = false ;
		myKey110 .showCustomChakra = false ;
		myKey110 .customChakraLayout = new String[] {} ;
		myKey110.showIcon = false ;
		myKey110 .icon = "";
		myKey110 .changeLayout = false ;
		myKey110 .layout = "";
		myKey.set(110, myKey110 );

		KeyAttr myKey111 = new KeyAttr();
		myKey111 .code = 112;
		myKey111 .label = "3" ;//numeral 3
		myKey111 .showChakra = false ;
		myKey111 .showCustomChakra = false ;
		myKey111 .customChakraLayout = new String[] {} ;
		myKey111.showIcon = false ;
		myKey111 .icon = "";
		myKey111 .changeLayout = false ;
		myKey111 .layout = "";
		myKey.set(111, myKey111 );

		KeyAttr myKey112 = new KeyAttr();
		myKey112 .code = 113;
		myKey112 .label = "4" ;//numeral 4
		myKey112 .showChakra = false ;
		myKey112 .showCustomChakra = false ;
		myKey112 .customChakraLayout = new String[] {} ;
		myKey112.showIcon = false ;
		myKey112 .icon = "";
		myKey112 .changeLayout = false ;
		myKey112 .layout = "";
		myKey.set(112, myKey112 );

		KeyAttr myKey113 = new KeyAttr();
		myKey113 .code = 114;
		myKey113 .label = "5" ;//numeral 5
		myKey113 .showChakra = false ;
		myKey113 .showCustomChakra = false ;
		myKey113 .customChakraLayout = new String[] {} ;
		myKey113.showIcon = false ;
		myKey113 .icon = "";
		myKey113 .changeLayout = false ;
		myKey113 .layout = "";
		myKey.set(113, myKey113 );


		KeyAttr myKey114 = new KeyAttr();
		myKey114 .code = 115;
		myKey114 .label = "6" ;//numeral 6
		myKey114 .showChakra = false ;
		myKey114 .showCustomChakra = false ;
		myKey114 .customChakraLayout = new String[] {} ;
		myKey114.showIcon = false ;
		myKey114 .icon = "";
		myKey114 .changeLayout = false ;
		myKey114 .layout = "";
		myKey.set(114, myKey114 );

		KeyAttr myKey115 = new KeyAttr();
		myKey115 .code = 116;
		myKey115 .label = "7" ;//numeral 7
		myKey115 .showChakra = false ;
		myKey115 .showCustomChakra = false ;
		myKey115 .customChakraLayout = new String[] {} ;
		myKey115.showIcon = false ;
		myKey115 .icon = "";
		myKey115 .changeLayout = false ;
		myKey115 .layout = "";
		myKey.set(115, myKey115 );

		KeyAttr myKey116 = new KeyAttr();
		myKey116 .code = 117;
		myKey116 .label = "8" ;//numeral 8
		myKey116 .showChakra = false ;
		myKey116 .showCustomChakra = false ;
		myKey115 .customChakraLayout = new String[] {} ;
		myKey116.showIcon = false ;
		myKey116 .icon = "";
		myKey116 .changeLayout = false ;
		myKey116 .layout = "";
		myKey.set(116, myKey116 );

		KeyAttr myKey117 = new KeyAttr();
		myKey117 .code = 118;
		myKey117 .label = "9" ;//numeral 9
		myKey117 .showChakra = false ;
		myKey117 .showCustomChakra = false ;
		myKey117 .customChakraLayout = new String[] {} ;
		myKey117.showIcon = false ;
		myKey117 .icon = "";
		myKey117 .changeLayout = false ;
		myKey117 .layout = "";
		myKey.set(117, myKey117 );

		KeyAttr myKey118 = new KeyAttr();
		myKey118 .code = 119;
		myKey118 .label = "0" ;//numeral 0
		myKey118 .showChakra = false ;
		myKey118 .showCustomChakra = false ;
		myKey118 .customChakraLayout = new String[] {} ;
		myKey118.showIcon = false ;
		myKey118 .icon = "";
		myKey118 .changeLayout = false ;
		myKey118 .layout = "";
		myKey.set(118, myKey118 );


		KeyAttr myKey119 = new KeyAttr();
		myKey119 .code = 120;
		myKey119 .label = "\u0A81" ;//chandrabindu
		myKey119 .showChakra = false ;
		myKey119.showCustomChakra = false ;
		myKey119 .customChakraLayout = new String[] {} ;
		myKey119.showIcon = false ;
		myKey119 .icon = "";
		myKey119 .changeLayout = false ;
		myKey119 .layout = "";
		myKey.set(119, myKey119 );

		KeyAttr myKey120 = new KeyAttr();
		myKey120 .code = 121;
		myKey120 .label = "\u0ABC" ; //nukta
		myKey120 .showChakra = false ;
		myKey120.showCustomChakra = false ;
		myKey120 .customChakraLayout = new String[] {} ;
		myKey120.showIcon = true ;
		myKey120 .icon = "nukta";
		myKey120 .changeLayout = false ;
		myKey120 .layout = "";
		myKey120 .isException = false;
		myKey.set(120, myKey120 );

		KeyAttr myKey121 = new KeyAttr();
		myKey121 .code = 122;
		myKey121 .label = "\u0AC9" ;//o ki matra as in doctor
		myKey121 .showChakra = false ;
		myKey121 .showCustomChakra = false ;
		myKey121 .customChakraLayout = new String[] {} ;
		myKey121 .showIcon = false ;
		myKey121 .icon = "";
		myKey121 .changeLayout = false ;
		myKey121 .layout = "";
		myKey.set(121, myKey121 );

		KeyAttr myKey122 = new KeyAttr();
		myKey122.code = 123;
		myKey122.label = "\u0A8D" ;//a as in apple
		myKey122.showChakra = false ;
		myKey122.showCustomChakra = false ;
		myKey122.customChakraLayout = new String[] {};
		myKey122.showIcon = false ;
		myKey122.icon = "";
		myKey122.changeLayout = false ;
		myKey122.layout = "";
		myKey.set(122, myKey122);

		KeyAttr myKey123 = new KeyAttr();
		myKey123.code = 124;
		myKey123.label = "\u0A91" ;//o as in orange
		myKey123.showChakra = false ;
		myKey123.showCustomChakra = false ;
		myKey123.customChakraLayout = new String[] {};
		myKey123.showIcon = false ;
		myKey123.icon = "";
		myKey123.changeLayout = false ;
		myKey123.layout = "";
		myKey.set(123, myKey123);

		KeyAttr myKey124 = new KeyAttr();
		myKey124  .code = 125;
		myKey124  .label = "[" ;//open square bracket
		myKey124  .showChakra = false ;
		myKey124  .showCustomChakra = false ;
		myKey124 .customChakraLayout = new String[] {} ;
		myKey124 .showIcon = false ;
		myKey124  .icon = "";
		myKey124  .changeLayout = false ;
		myKey124  .layout = "";
		myKey.set(124, myKey124 );

		KeyAttr myKey125 = new KeyAttr();
		myKey125 .code = 126;
		myKey125 .label = "]" ;//close square bracket
		myKey125 .showChakra = false ;
		myKey125 .showCustomChakra = false ;
		myKey125 .customChakraLayout = new String[] {} ;
		myKey125.showIcon = false ;
		myKey125 .icon = "";
		myKey125 .changeLayout = false ;
		myKey125 .layout = "";
		myKey.set(125, myKey125 );


		KeyAttr myKey126 = new KeyAttr();
		myKey126 .code = 127;
		myKey126 .label = "`" ;//prime
		myKey126 .showChakra = false ;
		myKey126 .showCustomChakra = false ;
		myKey126 .customChakraLayout = new String[] {} ;
		myKey126.showIcon = false ;
		myKey126 .icon = "";
		myKey126 .changeLayout = false ;
		myKey126 .layout = "";
		myKey.set(126, myKey126 );



		KeyAttr myKey127 = new KeyAttr();
		myKey127 .code = 128;
		myKey127 .label = "\u00AC" ;//'not' sign
		myKey127 .showChakra = false ;
		myKey127 .showCustomChakra = false ;
		myKey127 .customChakraLayout = new String[] {} ;
		myKey127.showIcon = false ;
		myKey127 .icon = "";
		myKey127 .changeLayout = false ;
		myKey127 .layout = "";
		myKey.set(127, myKey127 );

		KeyAttr myKey128 = new KeyAttr();
		myKey128 .code = 129;
		myKey128 .label = "\u221A" ;//sq. root
		myKey128 .showChakra = false ;
		myKey128 .showCustomChakra = false ;
		myKey128 .customChakraLayout = new String[] {} ;
		myKey128.showIcon = false ;
		myKey128 .icon = "";
		myKey128 .changeLayout = false ;
		myKey128 .layout = "";
		myKey.set(128, myKey128 );

		KeyAttr myKey129 = new KeyAttr();
		myKey129 .code = 130;
		myKey129 .label = "\u0AF0" ;//Gujarati abbreviation sign
		myKey129 .showChakra = false ;
		myKey129 .showCustomChakra = false ;
		myKey129 .customChakraLayout = new String[] {} ;
		myKey129.showIcon = false ;
		myKey129 .icon = "";
		myKey129 .changeLayout = false ;
		myKey129 .layout = "";
		myKey.set(129, myKey129 );

		KeyAttr myKey130 = new KeyAttr();
		myKey130 .code = 131;
		myKey130 .label = "\u0AEA" ;//Gujarati digit four repeated from key 62
		myKey130 .showChakra = false ;
		myKey130 .showCustomChakra = false ;
		myKey130 .customChakraLayout = new String[] {} ;
		myKey130.showIcon = false ;
		myKey130 .icon = "";
		myKey130 .changeLayout = false ;
		myKey130 .layout = "";
		myKey.set(130, myKey130 );

		KeyAttr myKey131 = new KeyAttr();
		myKey131 .code = 132;
		myKey131 .label = "¶" ;//not sure
		myKey131 .showChakra = false ;
		myKey131 .showCustomChakra = false ;
		myKey131 .customChakraLayout = new String[] {} ;
		myKey131.showIcon = false ;
		myKey131 .icon = "";
		myKey131 .changeLayout = false ;
		myKey131 .layout = "";
		myKey.set(131, myKey131);

		KeyAttr myKey132 = new KeyAttr();
		myKey132 .code = 133;
		myKey132 .label = "\u0AF1" ;//Gujarati Rupee symbol
		myKey132 .showChakra = false ;
		myKey132 .showCustomChakra = false ;
		myKey132 .customChakraLayout = new String[] {} ;
		myKey132.showIcon = false ;
		myKey132 .icon = "";
		myKey132 .changeLayout = false ;
		myKey132 .layout = "";
		myKey.set(132, myKey132);

		KeyAttr myKey133 = new KeyAttr();
		myKey133 .code = 134;
		myKey133 .label = "\u03C0" ;//not sure
		myKey133 .showChakra = false ;
		myKey133 .showCustomChakra = false ;
		myKey133.customChakraLayout = new String[] {} ;
		myKey133.showIcon = false ;
		myKey133 .icon = "";
		myKey133.changeLayout = false ;
		myKey133 .layout = "";
		myKey.set(133, myKey133);

		KeyAttr myKey134 = new KeyAttr();
		myKey134 .code = 135;
		myKey134 .label = "\u00A6" ;//not sure
		myKey134 .showChakra = false ;
		myKey134 .showCustomChakra = false ;
		myKey134.customChakraLayout = new String[] {} ;
		myKey134.showIcon = false ;
		myKey134 .icon = "";
		myKey134.changeLayout = false ;
		myKey134 .layout = "";
		myKey.set(134, myKey134);

		KeyAttr myKey135  = new KeyAttr();
		myKey135.code = 136;
		myKey135.label = "\u0AC3" ;//rru
		myKey135.showChakra = true ;
		myKey135.showCustomChakra = true ;
		myKey135.customChakraLayout = new String[] {"\u200C","\u0A8B","\u0AE0","\u0A8D", "\u0A91","\u200C","\u200C","\u200C","\u0AC4", "\u0AC3"} ;
		myKey135.showIcon = false  ;
		myKey135.icon = "";
		myKey135.changeLayout = false ;
		myKey135.layout = "";
		myKey.set(135, myKey135);

	}

	@Override
	@SuppressLint("UseSparseArrays")
	public HashMap<Integer, KeyAttr> hashThis() {
		HashMap<Integer, KeyAttr> hashed = new HashMap<Integer, KeyAttr>();
		for (int i = 0; i < nKeys; i++) {
			KeyAttr key = myKey.get(i);
			hashed.put(key.code, key);
		}
		return hashed;
	}
}