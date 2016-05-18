package org.iocaste.appbuilder.common;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.factories.SpecFactory;
import org.iocaste.appbuilder.common.factories.StandardContainerFactory;
import org.iocaste.appbuilder.common.factories.TabbedPaneFactory;
import org.iocaste.appbuilder.common.factories.TabbedPaneItemFactory;
import org.iocaste.appbuilder.common.factories.TableToolFactory;
import org.iocaste.appbuilder.common.factories.TextEditorFactory;
import org.iocaste.appbuilder.common.factories.TextFactory;
import org.iocaste.appbuilder.common.factories.TextFieldFactory;
import org.iocaste.appbuilder.common.factories.TilesFactory;
import org.iocaste.appbuilder.common.factories.ButtonFactory;
import org.iocaste.appbuilder.common.factories.DataFormFactory;
import org.iocaste.appbuilder.common.factories.ExpandBarFactory;
import org.iocaste.appbuilder.common.factories.FileUploadFactory;
import org.iocaste.appbuilder.common.factories.FormFactory;
import org.iocaste.appbuilder.common.factories.LinkFactory;
import org.iocaste.appbuilder.common.factories.ListBoxFactory;
import org.iocaste.appbuilder.common.factories.NavControlFactory;
import org.iocaste.appbuilder.common.factories.NodeListFactory;
import org.iocaste.appbuilder.common.factories.ParameterFactory;
import org.iocaste.appbuilder.common.factories.PrintAreaFactory;
import org.iocaste.appbuilder.common.factories.RadioButtonFactory;
import org.iocaste.appbuilder.common.factories.RadioGroupFactory;
import org.iocaste.appbuilder.common.factories.ReportToolFactory;
import org.iocaste.appbuilder.common.factories.SkipFactory;
import org.iocaste.appbuilder.common.navcontrol.NavControl;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.StyleSheet;

public class BuilderCustomView extends AbstractCustomView {
    private Map<ViewSpecItem.TYPES, SpecFactory> factories;
    
    public BuilderCustomView() {
        factories = new HashMap<>();
        factories.put(ViewSpecItem.TYPES.BUTTON,
                new ButtonFactory());
        factories.put(ViewSpecItem.TYPES.DATA_FORM,
                new DataFormFactory());
        factories.put(ViewSpecItem.TYPES.FILE_UPLOAD,
                new FileUploadFactory());
        factories.put(ViewSpecItem.TYPES.FORM,
                new FormFactory());
        factories.put(ViewSpecItem.TYPES.EXPAND_BAR,
                new ExpandBarFactory());
        factories.put(ViewSpecItem.TYPES.LINK,
                new LinkFactory());
        factories.put(ViewSpecItem.TYPES.LISTBOX,
                new ListBoxFactory());
        factories.put(ViewSpecItem.TYPES.NODE_LIST,
                new NodeListFactory());
        factories.put(ViewSpecItem.TYPES.PAGE_CONTROL,
                new NavControlFactory());
        factories.put(ViewSpecItem.TYPES.PARAMETER,
                new ParameterFactory());
        factories.put(ViewSpecItem.TYPES.PRINT_AREA,
                new PrintAreaFactory());
        factories.put(ViewSpecItem.TYPES.REPORT_TOOL,
                new ReportToolFactory());
        factories.put(ViewSpecItem.TYPES.SKIP,
                new SkipFactory());
        factories.put(ViewSpecItem.TYPES.STANDARD_CONTAINER,
                new StandardContainerFactory());
        factories.put(ViewSpecItem.TYPES.TABBED_PANE,
                new TabbedPaneFactory());
        factories.put(ViewSpecItem.TYPES.TABBED_PANE_ITEM,
                new TabbedPaneItemFactory());
        factories.put(ViewSpecItem.TYPES.TEXT_EDITOR,
                new TextEditorFactory());
        factories.put(ViewSpecItem.TYPES.TABLE_TOOL,
                new TableToolFactory());
        factories.put(ViewSpecItem.TYPES.TEXT,
                new TextFactory());
        factories.put(ViewSpecItem.TYPES.TEXT_FIELD,
                new TextFieldFactory());
        factories.put(ViewSpecItem.TYPES.TILES,
                new TilesFactory());
        factories.put(ViewSpecItem.TYPES.RADIO_BUTTON,
                new RadioButtonFactory());
        factories.put(ViewSpecItem.TYPES.RADIO_GROUP,
                new RadioGroupFactory());
    }
    
    private void buildItem(PageBuilderContext context,
            ViewComponents components, ViewSpecItem item, String prefix) {
        SpecFactory factory;
        
        factory = getFactory(item);
        if (factory != null)
            factory.run(context, components, item, prefix);
        
        for (ViewSpecItem child : item.getItems())
            buildItem(context, components, child, prefix);
    }
    
    private final void download(PageBuilderContext context) throws Exception {
        File file = new File(context.downloaddata.fullname);
        FileInputStream fis = new FileInputStream(file);
        byte[] content = new byte[fis.available()];
        
        fis.read(content);
        fis.close();
        
        context.function.setContentType(
                context.downloaddata.contenttype);
        context.function.setContentEncoding(
                context.downloaddata.contentencoding);
        context.function.setHeader("Content-Disposition",
                new StringBuilder("attachment; filename=\"").
                append(context.downloaddata.filename).
                append("\"").toString());
        context.view.setContent(content);
    }
    
    @Override
    public void execute(AbstractContext context, ViewSpecItem itemspec,
            String prefix) {
        ComponentEntry entry;
        SpecFactory factory;
        PageBuilderContext _context = (PageBuilderContext)context;
        ViewComponents components = new ViewComponents();;
        ViewSpec spec = getViewSpec();
        ViewConfig config = getViewConfig();
        ViewInput input = getViewInput();
        
        spec.run(itemspec, _context);
        for (ViewSpecItem item : spec.getItems())
            buildItem(_context, components, item, prefix);
        
        if (config != null)
            config.run(_context, prefix);
        
        if (input != null)
            input.run(_context, true, prefix);

        for (String key : components.entries.keySet()) {
            entry = components.entries.get(key);
            factory = factories.get(entry.data.type);
            if (factory == null)
                continue;
            factory.generate(entry, prefix);
            if (entry.component == null)
                continue;
            entry.component.run();
            entry.component.refresh();
        }
        
        for (ViewSpecItem.TYPES type : ViewSpecItem.TYPES.values()) {
            factory = factories.get(type);
            if (factory != null)
                factory.generate(components);
        }
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.CustomView#execute(
     *    org.iocaste.shell.common.AbstractContext)
     */
    @Override
    public void execute(AbstractContext context) throws Exception {
        ComponentEntry entry;
        SpecFactory factory;
        NavControl navcontrol;
        ViewContext viewctx;
        ViewComponents components;
        StyleSheet stylesheet;
        ViewSpec viewspec = getViewSpec();
        ViewConfig viewconfig = getViewConfig();
        ViewInput viewinput = getViewInput();
        PageBuilderContext _context = (PageBuilderContext)context;
        
        if (_context.downloaddata != null) {
            download(_context);
            _context.downloaddata = null;
            return;
        }

        viewctx = _context.getView(getView());
        components = viewctx.getComponents();
        if (!viewspec.isInitialized()) {
            stylesheet = DefaultApplicationStyle.instance();
            _context.view.clear();
            _context.view.setStyleSheet(stylesheet.getElements());
            _context.function.unregisterValidators();
            viewspec.run(_context);
            viewctx.reset();
            
            for (ViewSpecItem item : viewspec.getItems())
                buildItem(_context, components, item, null);

            navcontrol = factories.get(ViewSpecItem.TYPES.PAGE_CONTROL).get();
            if (viewconfig != null) {
                viewconfig.setNavControl(navcontrol);
                viewconfig.run(_context);
            }

            if (navcontrol != null)
                navcontrol.build(_context);
            
            viewspec.setInitialized(!viewctx.isUpdatable());
            if (viewinput != null)
                viewinput.run(_context, true);

            for (String key : components.entries.keySet()) {
                entry = components.entries.get(key);
                factory = factories.get(entry.data.type);
                if (factory == null)
                    continue;
                factory.generate(entry);
                entry.component.run();
                entry.component.refresh();
            }
            
            for (ViewSpecItem.TYPES type : ViewSpecItem.TYPES.values()) {
                factory = factories.get(type);
                if (factory != null)
                    factory.generate(components);
            }
            return;
        }
        
        if (viewinput != null) {
            viewinput.run(_context, false);
            for (String key : components.entries.keySet()) {
                entry = components.entries.get(key);
                factory = factories.get(entry.data.type);
                if (factory == null)
                    continue;
                entry.component.refresh();
            }
            
            for (ViewSpecItem item : viewspec.getItems()) {
                factory = getFactory(item);
                if (factory != null)
                    factory.update(components);
            }
        }
    }
    
    private final SpecFactory getFactory(ViewSpecItem item) {
        ViewSpecItem.TYPES[] types = ViewSpecItem.TYPES.values();
        
        return factories.get(types[item.getType()]);
    }
}