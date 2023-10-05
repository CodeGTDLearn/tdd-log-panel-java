package com.com.tddconsolelog.panel;

import java.util.ArrayList;
import java.util.stream.Stream;

public class ConsolePanelUtil {

  public static void main(String[] args) {

    var panel1 = scalablePanel(21, "Simple Panel Scalable", "My First Topic");
    System.out.println(panel1);

    var panel2 = simplePanel("Simple Panel Scalable", "My First Topic");
    System.out.println(panel2);

    var panel3 = simplePanel("Only Title Test");
    System.out.println(panel3);

    var panel4 = simplePanelPlus(
         false,
         true,
         true,
         5,
         "no Divider | Yes for laterals",
         "Topic 1",
         "Topic 2"
    );
    System.out.println(panel4);

    var panel5 = simplePanelPlus(
         true,
         false,
         false,
         5,
         "Yes for Divider | No for laterals",
         "Topic 1",
         "Topic 2"
    );
    System.out.println(panel5);

    var panel6 = simplePanelPlus(
         true,
         true,
         true,
         5,
         "Yes for Divider | Yes for laterals",
         "Topic 1",
         "Topic 2"
    );
    System.out.println(panel6);

    var panel7 = simplePanelPlus(
         true,
         true,
         false,
         2,
         "Yes for Divider | Yes for laterals"
    );
    System.out.println(panel7);

    var panel8 = fullPanel(
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
         true,
         true,
         true,
         "Full Panel", "My First Topic text", "My Second Topic text"
    );
    System.out.println(panel8);

    var panel9 = fullPanel(
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
         false,
         false,
         false,
         "Full Panel No Lateral/Divider", "First Topic", "Second Topic"
    );
    System.out.println(panel9);

    var panel10 = fullPanel(
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
         false,
         false,
         false,
         "Full Panel No Lateral/Divider"
    );
    System.out.println(panel10);
  }

  public static String simplePanel(String... texts) {

    return fullPanel(
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
         true,
         true,
         true,
         texts
    );
  }

  public static String simplePanelPlus(
       boolean enableDivider,
       boolean enableLateralFaces,
       boolean enableNumericTopics,
       int margin,
       String... texts) {

    return fullPanel(
         21,
         margin,
         1,
         1,
         Border.DOUBLE,
         Border.DOUBLE,
         Border.THIN,
         Border.THIN,
         true,
         true,
         enableLateralFaces,
         enableDivider,
         enableNumericTopics,
         texts
    );
  }

  public static String scalablePanel(int scale, String... texts) {

    return fullPanel(
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
         true,
         true,
         true,
         texts
    );
  }

  public static String fullPanel(
       int scale,
       int margin,
       int headerSpaces,
       int footerSpaces,
       Border cornerFormat,
       Border centerMarkFormat,
       Border horizontalFaceFormat,
       Border lateralFaceFormat,
       boolean capitalizeTitle,
       boolean centralizeTitle,
       boolean enableLateralFaces,
       boolean enableDivider,
       boolean enableNumericTopics,
       String... titleAndTopics) {

    /*╔════════════════════════════════════════════════════════════════╗
      ║              Adjustment Factor for the Tile                    ║
      ╠════════════════════════════════════════════════════════════════╣
      ║ This value can be 'positive' or 'negative'                     ║
      ║ and should be changed untill the title become well centralized ║
      ╚════════════════════════════════════════════════════════════════╝*/
    var adjustmentFactorForTitle = - 1;
    var title = titleAndTopics[0];
    var marginTitle = scale - ((title.length() / 2) - adjustmentFactorForTitle);
/*    String[] formattedTexts =
         Stream.of(titleAndTopics)
               .map(textItem -> textItem.equals(title) && centralizeTitle ?
                    " ".repeat(marginTitle) + title : textItem)
               .map(textItem -> textItem.equals(title) && capitalizeTitle ?
                    textItem.toUpperCase() : textItem)
               .toArray();
               */
    Object[] formattedTexts =
         titleAndTopics.length > 1
              ? Stream.of(titleAndTopics)
                      .map(textItem ->
                                textItem.equals(title) && centralizeTitle ?
                                     " ".repeat(marginTitle) + title
                                     : textItem)
                      .map(textItem ->
                                textItem.equals(title) && capitalizeTitle ?
                                     textItem.toUpperCase()
                                     : textItem)
                      .toArray()
              : titleAndTopics;

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
         upperLineCreator(scale, cornerFormat, centerMarkFormat, horizontalFaceFormat);

    var dividerFace = enableDivider ?
         middleLineCreator(scale, cornerFormat, centerMarkFormat, horizontalFaceFormat)
         : "";

    var bottomFace =
         bottomLineCreator(scale, cornerFormat, centerMarkFormat, horizontalFaceFormat);

    var rightFace = enableLateralFaces ? formattingFace(lateralFaceFormat) : "";
    var leftFace = enableLateralFaces ? formattingFace(lateralFaceFormat) : "";

    var fillingUpTitleExcedentSpaces = String.valueOf(fullSize);
    var textSccafold = new StringBuilder();
    textSccafold.append(header)
                .append(upperFace)
                .append(rightFace)
                .append("%s%%-%ss".formatted(marginTopic, fillingUpTitleExcedentSpaces))
                .append(leftFace)
                .append("\n")
                .append(formattedTexts.length > 1 ? dividerFace : "");


    var fillingUpTopicExcedentSpaces = String.valueOf(fullSize - 4);
    var topicEnumeration = 0;
    var symbolEnumerator = '\u2070';
    var parenthesisEnumerator = ')';

    for (int i = formattedTexts.length - 1; i > 0; i--) {
      ++ topicEnumeration;
      textSccafold
           .append(rightFace)
           .append("%s%s%s%s %%-%ss".formatted(
                marginTopic,
                enableNumericTopics ? topicEnumeration : "",
                enableNumericTopics ? symbolEnumerator : "",
                enableNumericTopics ? parenthesisEnumerator : "",
                fillingUpTopicExcedentSpaces
           ))
           .append(rightFace)
           .append("\n");
    }

    textSccafold.append(bottomFace)
                .append(footer);

    //    System.out.printf(textSccafold.toString(), formattedTexts);
    //    System.out.println(textSccafold.toString().formatted(formattedTexts));
    return textSccafold.toString()
                       .formatted(formattedTexts);
  }

  private static String faceGenerator(int scale, char BASE_LINE) {

    var sb = new StringBuilder();
    while (sb.length() < scale) sb.append(BASE_LINE);
    return sb.toString();
  }

  private static String upperLineCreator(
       int scale,
       Border corner,
       Border centerMark,
       Border line) {

    ArrayList<Character> borders = new ArrayList<>();
    switch (corner) {
      case BOLD -> {
        borders.add(BoldFont.UPPER_LEFT_CORNER.code);
        borders.add(BoldFont.UPPER_RIGHT_CORNER.code);
      }
      case THIN -> {
        borders.add(ThinFont.UPPER_LEFT_CORNER.code);
        borders.add(ThinFont.UPPER_RIGHT_CORNER.code);
      }
      case DOUBLE -> {
        borders.add(DoubleFont.UPPER_LEFT_CORNER.code);
        borders.add(DoubleFont.UPPER_RIGHT_CORNER.code);
      }
    }

    switch (centerMark) {
      case BOLD -> borders.add(BoldFont.BASE_LINE.code);
      case THIN -> borders.add(ThinFont.BASE_LINE.code);
      case DOUBLE -> borders.add(DoubleFont.BASE_LINE.code);
    }

    switch (line) {
      case BOLD -> borders.add(BoldFont.BASE_LINE.code);
      case THIN -> borders.add(ThinFont.BASE_LINE.code);
      case DOUBLE -> borders.add(DoubleFont.BASE_LINE.code);
    }

    var baseline = faceGenerator(scale, borders.get(3));

    return
         new StringBuilder(baseline)
              .insert(0, borders.get(0))
              .append(borders.get(2))
              .append(baseline)
              .append(borders.get(1))
              .append("\n")
              .toString();
  }

  private static String middleLineCreator(
       int scale,
       Border corner,
       Border centerMark,
       Border baseLine) {

    ArrayList<Character> borders = new ArrayList<>();
    switch (corner) {
      case BOLD -> {
        borders.add(BoldFont.MIDDLE_LEFT.code);
        borders.add(BoldFont.MIDDLE_RIGHT.code);
      }
      case THIN -> {
        borders.add(ThinFont.MIDDLE_LEFT.code);
        borders.add(ThinFont.MIDDLE_RIGHT.code);
      }
      case DOUBLE -> {
        borders.add(DoubleFont.MIDDLE_LEFT.code);
        borders.add(DoubleFont.MIDDLE_RIGHT.code);
      }
    }

    switch (centerMark) {
      case BOLD -> borders.add(BoldFont.BASE_LINE.code);
      case THIN -> borders.add(ThinFont.BASE_LINE.code);
      case DOUBLE -> borders.add(DoubleFont.BASE_LINE.code);
    }

    switch (baseLine) {
      case BOLD -> borders.add(BoldFont.BASE_LINE.code);
      case THIN -> borders.add(ThinFont.BASE_LINE.code);
      case DOUBLE -> borders.add(DoubleFont.BASE_LINE.code);
    }

    var divider = faceGenerator(scale, borders.get(3));

    return
         new StringBuilder(divider)
              .insert(0, borders.get(0))
              .append(borders.get(2))
              .append(divider)
              .append(borders.get(1))
              .append("\n")
              .toString();
  }

  private static String bottomLineCreator(
       int scale,
       Border corner,
       Border centerMark,
       Border baseLine) {

    ArrayList<Character> borders = new ArrayList<>();

    switch (corner) {
      case BOLD -> {
        borders.add(BoldFont.LOWER_LEFT_CORNER.code);
        borders.add(BoldFont.LOWER_RIGHT_CORNER.code);
      }
      case THIN -> {
        borders.add(ThinFont.LOWER_LEFT_CORNER.code);
        borders.add(ThinFont.LOWER_RIGHT_CORNER.code);
      }
      case DOUBLE -> {
        borders.add(DoubleFont.LOWER_LEFT_CORNER.code);
        borders.add(DoubleFont.LOWER_RIGHT_CORNER.code);
      }
    }

    switch (centerMark) {
      case BOLD -> borders.add(BoldFont.BASE_LINE.code);
      case THIN -> borders.add(ThinFont.BASE_LINE.code);
      case DOUBLE -> borders.add(DoubleFont.BASE_LINE.code);
    }

    switch (baseLine) {
      case BOLD -> borders.add(BoldFont.BASE_LINE.code);
      case THIN -> borders.add(ThinFont.BASE_LINE.code);
      case DOUBLE -> borders.add(DoubleFont.BASE_LINE.code);
    }

    var baseline = faceGenerator(scale, borders.get(3));

    return
         new StringBuilder(baseline)
              .insert(0, borders.get(0))
              .append(borders.get(2))
              .append(baseline)
              .append(borders.get(1))
              .append("\n")
              .toString();
  }

  private static Character formattingFace(Border faceFormat) {

    return switch (faceFormat) {
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