/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: CaptchaEngineer.java 453 2009-09-12 17:57:34Z calvinxiu $
 */
package edu.hunter.modules.web.captcha;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.ImageFilter;

import com.jhlabs.image.WaterFilter;
import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.UniColorBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomListColorGenerator;
import com.octo.captcha.component.image.deformation.ImageDeformation;
import com.octo.captcha.component.image.deformation.ImageDeformationByFilters;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.DecoratedRandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.textpaster.textdecorator.TextDecorator;
import com.octo.captcha.component.image.wordtoimage.DeformedComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.FileDictionary;
import com.octo.captcha.component.word.wordgenerator.ComposeDictionaryWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;

public class CaptchaEngineer extends ListImageCaptchaEngine {

	public static final int MIN_FONT_SIZE = 18;
	public static final int MAX_FONT_SIZE = 20;
	public static final int IMG_WIDTH = 60;
	public static final int IMG_HEIGHT = 30;
	public static final int MIN_WORD_LEN = 4;
	public static final int MAX_WORD_LEN = 4;

	@Override
	protected void buildInitialFactories() {

		WordGenerator dictionnaryWords = new ComposeDictionaryWordGenerator(new FileDictionary("toddlist"));

		Font[] fontsList = new Font[] { Font.decode("Arial"), Font.decode("Tahoma"), Font.decode("Verdana") };
		FontGenerator font = new RandomFontGenerator(MIN_FONT_SIZE, MAX_FONT_SIZE, fontsList);

		BackgroundGenerator background = new UniColorBackgroundGenerator(IMG_WIDTH, IMG_HEIGHT, Color.white);

		// word2image components
		RandomListColorGenerator cgen = new RandomListColorGenerator(new Color[] { new Color(23, 170, 27),
				new Color(220, 34, 11), new Color(23, 67, 172) });

		// 文字干扰器
		// BaffleTextDecorator baffleTextDecorator = new BaffleTextDecorator(2, new
		// SingleColorGenerator(Color.green));// 气泡干扰
		// LineTextDecorator lineTextDecorator = new LineTextDecorator(1, new SingleColorGenerator(Color.green));// 曲线干扰
		TextPaster randomPaster = new DecoratedRandomTextPaster(MIN_WORD_LEN, MAX_WORD_LEN, cgen,
				new TextDecorator[] {});

		ImageFilter filter = buildFilter();
		ImageDeformation postDef = new ImageDeformationByFilters(new ImageFilter[] {});
		ImageDeformation backDef = new ImageDeformationByFilters(new ImageFilter[] {});
		ImageDeformation textDef = new ImageDeformationByFilters(new ImageFilter[] { filter });

		WordToImage word2image = new DeformedComposedWordToImage(font, background, randomPaster, backDef, textDef,
				postDef);
		addFactory(new GimpyFactory(dictionnaryWords, word2image));
	}

	/**
	 * 干扰器
	 * 
	 * @return
	 */
	private ImageFilter buildFilter() {

		WaterFilter water = new WaterFilter();
		water.setAmplitude(2d);// 振幅
		water.setAntialias(true);// 显示字会出现锯齿状,true就是平滑的
		water.setPhase(30d);// 月亮的盈亏
		water.setWavelength(30d);//
		return water;
	}

}
