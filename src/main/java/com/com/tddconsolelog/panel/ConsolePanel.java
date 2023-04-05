package com.com.tddconsolelog.panel;

import java.util.ArrayList;
import java.util.stream.Stream;

public class ConsolePanel {

  public static void main(String[] args) {

    scalablePanel(21, "Simple Panel Scalable", "My First Topic");

    simplePanel("Simple Panel Scalable", "My First Topic");

    panel(
         21,
         2,
         3,
         3,
         Border.BOLD,
         Border.DOUBLE,
         Border.DOUBLE,
         Border.THIN,
         true,
         true,
         "Full Panel", "My First Topic text", "My Second Topic text"
    );

  }

  public static void simplePanel(String... texts) {

    panel(
         21,
         5,
         1,
         1,
         Border.DOUBLE,
         Border.DOUBLE,
         Border.THIN,
         Border.THIN,
         true,
         true,
         texts
    );
  }

  public static void scalablePanel(int scale, String... texts) {

    panel(
         scale,
         5,
         1,
         1,
         Border.DOUBLE,
         Border.DOUBLE,
         Border.THIN,
         Border.THIN,
         true,
         true,
         texts
    );
  }

  public static void panel(
       int scale,
       int margin,
       int headerSpaces,
       int footerSpaces,
       Border cornerFormat,
       Border centerMarkFormat,
       Border horizontalFaceFormat,
       Border verticalFaceFormat,
       boolean isUppercasedTitle,
       boolean isCentralizedTitle,
       String... titleAndTopics) {

    var estimatedAdjustmentFactor = 3;
    var title = titleAndTopics[0];
    var marginTitle = scale - (title.length() / 2) - estimatedAdjustmentFactor;
    var formattedTexts =
         Stream.of(titleAndTopics)
               .map(item -> item.equals(title) && isCentralizedTitle ?
                    " ".repeat(marginTitle) + title : item)
               .map(item -> item.equals(title) && isUppercasedTitle ?
                    item.toUpperCase() : item)
               .toArray();

    var marginLimitedBySize = Math.min(margin, scale);

    /*╔════════════════════════════════════════╗
      ║ scale + margin discrepacies eliminated ║
      ╚════════════════════════════════════════╝*/
    if (marginLimitedBySize % 2 != 0) -- marginLimitedBySize;
    if (scale % 2 != 0) ++ scale;

    int fullSize = (scale * 2) - marginLimitedBySize;
    if (fullSize % 2 == 0) ++ fullSize;
    else -- fullSize;

    var marginTopic = " ".repeat(marginLimitedBySize);
    var header = "\n".repeat(headerSpaces);
    var footer = "\n".repeat(footerSpaces);

    var upperFace =
         upperFaceCreator(scale, cornerFormat, centerMarkFormat, horizontalFaceFormat);

    var dividerFace =
         middleFaceCreator(scale, cornerFormat, centerMarkFormat, horizontalFaceFormat);

    var bottomFace =
         bottomFaceCreator(scale, cornerFormat, centerMarkFormat, horizontalFaceFormat);

    var rightFace = faceCreator(verticalFaceFormat);

    var fillingUpTitleExcedentSpaces = String.valueOf(fullSize);
    var textSccafold = new StringBuilder();
    textSccafold.append(header)
                .append(upperFace)
                .append(rightFace)
                .append("%s%%-%ss".formatted(marginTopic, fillingUpTitleExcedentSpaces))
                .append(rightFace)
                .append("\n")
                .append(dividerFace);


    var fillingUpTopicExcedentSpaces = String.valueOf(fullSize - 4);
    var topicEnumeration = 0;
    var symbolEnumerator = '\u2070';
    for (int i = formattedTexts.length - 1; i > 0; i--) {
      ++ topicEnumeration;
      textSccafold.append(rightFace)
                  .append("%s%s%s) %%-%ss".formatted(
                       marginTopic,
                       topicEnumeration,
                       symbolEnumerator,
                       fillingUpTopicExcedentSpaces
                  ))
                  .append(rightFace)
                  .append("\n");
    }
    textSccafold.append(bottomFace)
                .append(footer);

    System.out.printf(textSccafold.toString(), formattedTexts);
  }

  private static String generateLine(char baseChar, int scale, char BASE_LINE) {

    return
         String
              .valueOf(baseChar)
              .repeat(scale)
              .replace(baseChar, BASE_LINE);
  }

  private static String upperFaceCreator(
       int scale,
       Border corner,
       Border centerMark,
       Border line) {

    ArrayList<Character> borderStylingItems = new ArrayList<>();
    switch (corner) {
      case BOLD -> {
        borderStylingItems.add(BoldFont.UPPER_LEFT_CORNER.code);
        borderStylingItems.add(BoldFont.UPPER_RIGHT_CORNER.code);
      }
      case THIN -> {
        borderStylingItems.add(ThinFont.UPPER_LEFT_CORNER.code);
        borderStylingItems.add(ThinFont.UPPER_RIGHT_CORNER.code);
      }
      case DOUBLE -> {
        borderStylingItems.add(DoubleFont.UPPER_LEFT_CORNER.code);
        borderStylingItems.add(DoubleFont.UPPER_RIGHT_CORNER.code);
      }
    }

    switch (centerMark) {
      case BOLD -> borderStylingItems.add(BoldFont.BASE_LINE.code);
      case THIN -> borderStylingItems.add(ThinFont.BASE_LINE.code);
      case DOUBLE -> borderStylingItems.add(DoubleFont.BASE_LINE.code);
    }

    switch (line) {
      case BOLD -> borderStylingItems.add(BoldFont.BASE_LINE.code);
      case THIN -> borderStylingItems.add(ThinFont.BASE_LINE.code);
      case DOUBLE -> borderStylingItems.add(DoubleFont.BASE_LINE.code);
    }

    var baseline = generateLine('_', scale, borderStylingItems.get(3));

    return borderStylingItems.get(0) + baseline +
           borderStylingItems.get(2) + baseline +
           borderStylingItems.get(1) + "\n";
  }

  private static String middleFaceCreator(
       int scale,
       Border corner,
       Border centerMark,
       Border baseLine
  ) {

    ArrayList<Character> borderStylingItems = new ArrayList<>();
    switch (corner) {
      case BOLD -> {
        borderStylingItems.add(BoldFont.MIDDLE_LEFT.code);
        borderStylingItems.add(BoldFont.MIDDLE_RIGHT.code);
      }
      case THIN -> {
        borderStylingItems.add(ThinFont.MIDDLE_LEFT.code);
        borderStylingItems.add(ThinFont.MIDDLE_RIGHT.code);
      }
      case DOUBLE -> {
        borderStylingItems.add(DoubleFont.MIDDLE_LEFT.code);
        borderStylingItems.add(DoubleFont.MIDDLE_RIGHT.code);
      }
    }

    switch (centerMark) {
      case BOLD -> borderStylingItems.add(BoldFont.BASE_LINE.code);
      case THIN -> borderStylingItems.add(ThinFont.BASE_LINE.code);
      case DOUBLE -> borderStylingItems.add(DoubleFont.BASE_LINE.code);
    }

    switch (baseLine) {
      case BOLD -> borderStylingItems.add(BoldFont.BASE_LINE.code);
      case THIN -> borderStylingItems.add(ThinFont.BASE_LINE.code);
      case DOUBLE -> borderStylingItems.add(DoubleFont.BASE_LINE.code);
    }

    var divider = generateLine('_', scale, borderStylingItems.get(3));

    return borderStylingItems.get(0) + divider +
           borderStylingItems.get(2) + divider +
           borderStylingItems.get(1) + "\n";
  }

  private static String bottomFaceCreator(
       int scale,
       Border corner,
       Border centerMark,
       Border baseLine) {

    ArrayList<Character> borderStylingItems = new ArrayList<>();
    switch (corner) {
      case BOLD -> {
        borderStylingItems.add(BoldFont.LOWER_LEFT_CORNER.code);
        borderStylingItems.add(BoldFont.LOWER_RIGHT_CORNER.code);
      }
      case THIN -> {
        borderStylingItems.add(ThinFont.LOWER_LEFT_CORNER.code);
        borderStylingItems.add(ThinFont.LOWER_RIGHT_CORNER.code);
      }
      case DOUBLE -> {
        borderStylingItems.add(DoubleFont.LOWER_LEFT_CORNER.code);
        borderStylingItems.add(DoubleFont.LOWER_RIGHT_CORNER.code);
      }
    }

    switch (centerMark) {
      case BOLD -> borderStylingItems.add(BoldFont.BASE_LINE.code);
      case THIN -> borderStylingItems.add(ThinFont.BASE_LINE.code);
      case DOUBLE -> borderStylingItems.add(DoubleFont.BASE_LINE.code);
    }

    switch (baseLine) {
      case BOLD -> borderStylingItems.add(BoldFont.BASE_LINE.code);
      case THIN -> borderStylingItems.add(ThinFont.BASE_LINE.code);
      case DOUBLE -> borderStylingItems.add(DoubleFont.BASE_LINE.code);
    }

    var baseline = generateLine('_', scale, borderStylingItems.get(3));

    return borderStylingItems.get(0) + baseline +
           borderStylingItems.get(2) + baseline +
           borderStylingItems.get(1) + "\n";
  }

  private static Character faceCreator(Border corner) {

    return switch (corner) {
      case BOLD -> BoldFont.FACE_LINE.code;
      case THIN -> ThinFont.FACE_LINE.code;
      case DOUBLE -> DoubleFont.FACE_LINE.code;
    };
  }

  private enum BoldFont {

    FACE_LINE('\u2503'),
    BASE_LINE('\u2501'),
    UPPER_LEFT_CORNER('\u250F'),
    UPPER_RIGHT_CORNER('\u2513'),
    MIDDLE_LEFT('\u2523'),
    MIDDLE_RIGHT('\u252B'),
    LOWER_LEFT_CORNER('\u2517'),
    LOWER_RIGHT_CORNER('\u251B');

    private final char code;

    BoldFont(char code) {

      this.code = code;
    }
  }

  private enum ThinFont {

    FACE_LINE('\u2502'),
    BASE_LINE('\u2500'),
    UPPER_LEFT_CORNER('\u250C'),
    UPPER_RIGHT_CORNER('\u2510'),
    MIDDLE_LEFT('\u252C'),
    MIDDLE_RIGHT('\u2524'),
    LOWER_LEFT_CORNER('\u2514'),
    LOWER_RIGHT_CORNER('\u2518');

    private final char code;

    ThinFont(char code) {

      this.code = code;
    }
  }

  private enum DoubleFont {

    FACE_LINE('\u2551'),
    BASE_LINE('\u2550'),
    UPPER_LEFT_CORNER('\u2554'),
    UPPER_RIGHT_CORNER('\u2557'),
    MIDDLE_LEFT('\u2560'),
    MIDDLE_RIGHT('\u2563'),
    LOWER_LEFT_CORNER('\u255A'),
    LOWER_RIGHT_CORNER('\u255D');

    private final char code;

    DoubleFont(char code) {

      this.code = code;
    }
  }

  private enum Border {
    BOLD, THIN, DOUBLE
  }
}
//    private static String simpleLineStyle(String str) {
//
//      return str.replace('a', '\u250c')
//                .replace('b', '\u252c')
//                .replace('c', '\u2510')
//                .replace('d', '\u251c')
//                .replace('e', '\u253c')
//                .replace('f', '\u2524')
//                .replace('g', '\u2514')
//                .replace('h', '\u2534')
//                .replace('i', '\u2518')
//                .replace('_', '\u2500')
//                .replace('|', '\u2502');
//    }
//
//    private static String mixedLineStyle(String str) {
//      //source: https://en.wikipedia.org/wiki/Box-drawing_character
//      return str.replace('a', '\u250F')
//                .replace('b', '\u252c')
//                .replace('c', '\u2513')
//                .replace('d', '\u2523')
//                .replace('e', '\u253c')
//                .replace('f', '\u252B')
//                .replace('g', '\u2517')
//                .replace('h', '\u2534')
//                .replace('i', '\u251B')
//                .replace('-', '\u2501')
//                .replace('_', '\u2500')
//                .replace('*', '\u2501')
//                .replace('|', '\u2502');
//    }