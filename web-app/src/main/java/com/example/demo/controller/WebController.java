package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * ホームページに関するコントローラークラスです。
 */
@Controller
public class WebController {

	/**
	 * index画面を表示する。
	 *
	 * @param model ビューに渡すモデルオブジェクト
	 * @return index画面
	 */
	@GetMapping("/")
	public String index(Model model) {
		
		model.addAttribute("message","ようこそtestさん");
		model.addAttribute("datetime",LocalDateTime.now());
		
		return "index";
	}
	
	/**
	 * NullPointerExceptionを発生させる。
	 *
	 * @return 無し
	 */
	@GetMapping("/ex1")
	public String NullPointerException() {
		//String value = Math.random() < 1 ? null : "a";
		//String value = Math.random() < 1 ? null : "a"; // 常に null になります
		String value = null;
		
		System.out.println(value.toLowerCase());
		return "";
	}
	
	/*public String NullPointerException() {
	    String value = getNullString().toLowerCase(); // getNullString() が null を返すので、toLowerCase() 呼び出しで NullPointerException が発生します
	    System.out.println(value);
	    return "";
	}
	
	private String getNullString() {
	    return null;
	}*/
	
	/*public String NullPointerException() {
	    MyObject obj = new MyObject();
	    System.out.println(obj.getValue().toLowerCase()); // obj.getValue() が null を返すので、toLowerCase() 呼び出しで NullPointerException が発生します
	    return "";
	}
	
	class MyObject {
	    private String value = null;
	
	    public String getValue() {
	        return value;
	    }
	}*/
	
	/*public String NullPointerException() {
	    List<String> list = new ArrayList<>();
	    list.add(null); // null をリストに追加
	    System.out.println(list.get(0).toLowerCase()); // list.get(0) が null を返すので、toLowerCase() 呼び出しで NullPointerException が発生します
	    return "";
	}*/
	
	/**
	 * NumberFormatExceptionを発生させる。
	 *
	 * @return 無し
	 */
	@GetMapping("/ex2")
	public String NumberFormatException() {
		String value = "a";
		//String value = ""; // 空の文字列
		//String value = "++123"; // 複数の '+' 記号を含む
		//String value = "123.45"; // 小数点を含む
		//String value = "123abc"; // 数値の後にアルファベットが続く
		int num = Integer.parseInt(value);
		System.out.println(num);
		return "";
	}
	
	
	
	
	/**
	 * IndexOutOfBoundsExceptionを発生させる。
	 *
	 * @return 無し
	 */
	@GetMapping("/ex3")
	public String IndexOutOfBoundsException() {
		List<String> list = new ArrayList<>();
		list.get(1);
		//System.out.println(list.get(10)); // 空のリストでインデックス 10 にアクセスしようとする
		
		/*List<String> list = Arrays.asList("a", "b", "c");
		System.out.println(list.get(-1)); // 負のインデックス -1 にアクセスしようとする
		*/	
		
		/*int[] array = {1, 2, 3};
		System.out.println(array[5]); // 配列の範囲外（インデックス 5）にアクセスしようとする
		*/
		
		/*List<String> list = Arrays.asList("a", "b", "c", "d");
		List<String> sublist = list.subList(1, 3); // 部分リスト [b, c] を作成
		System.out.println(sublist.get(3)); // 部分リストの範囲外（インデックス 3）にアクセスしようとする
		*/		
		
		return "";
	}
	
}
