/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.xml;

/**
 * This enumerated type contains an entry
 * for any element the parser anticipates
 * finding within an xml file. This allows
 * for the 'string switching' method seen
 * in the XMLParser class.
 * 
 * @author  Alistair Jewers
 * @version 1.0 13 Feb 2015
 */
public enum XMLElement {
    SLIDESHOW,
    DOCUMENTINFO,
        AUTHOR,
        VERSION,
        COMMENT,
        GROUPID,
        DATECREATED,
        TOTALMARKS,
        LESSONNAME,
    DEFAULTSETTINGS,
        BACKGROUNDCOLOR,
        FONT,
        FONTSIZE,
        FONTCOLOR,
    GRADESETTINGS,
        PASSBOUNDARY,
    SLIDE,
        TEXT,
            RICHTEXT,
        IMAGE,
        AUDIO,
        VIDEO,
        GRAPHIC,
            CYCLICSHADING,
        ANSWERBOX,
        BUTTON,
            BUTTONTEXT,
            FUNCTION,
            VISIBLE,
            INTERACTION,
        MULTIPLECHOICE,
            ANSWER
}
