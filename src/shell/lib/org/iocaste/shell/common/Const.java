package org.iocaste.shell.common;

/**
 * Define constantes para elementos e status de mensagens.
 * 
 * @author francisco.prates
 *
 */
public enum Const {
    NONE(null),
    STATUS(null),
    ERROR(null),
    WARNING(null),
    BUTTON("button"),
    CALENDAR(null),
    CANVAS(null),
    CHECKBOX("checkbox"),
    DATA_FORM("form"),
    DATA_ITEM(null),
    DATA_VIEW(null),
    DETAILED(null),
    DUMMY(null),
    EXPAND_BAR("eb_external"),
    FILE_ENTRY(null),
    FORM("form_content"),
    FRAME("frame"),
    HTML_TAG(null),
    LINK("link"),
    LIST_BOX("list_box"),
    MESSAGE(null),
    NODE_LIST(null),
    NODE_LIST_ITEM(null),
    PARAMETER(null),
    PASSWORD(null),
    PRINT_AREA(null),
    RADIO_BUTTON(null),
    RADIO_GROUP(null),
    RANGE_FIELD("text_field"),
    SEARCH_HELP("sh_button"),
    SINGLE(null),
    STANDARD_CONTAINER(null),
    TABBED_PANE(null),
    TABBED_PANE_ITEM("tp_item"),
    TABLE("table_area"),
    TABLE_ITEM(null),
    TEXT("text"),
    TEXT_AREA("textarea"),
    TEXT_FIELD("text_field"),
    VIRTUAL(null),
    VIRTUAL_CONTROL(null);
    
    private String style;
    
    public final String style() {
        return style;
    }
    
    Const(String style) {
        this.style = style;
    }
}
