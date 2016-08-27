package org.iocaste.appbuilder.common;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.factories.SpecFactory;
import org.iocaste.appbuilder.common.navcontrol.NavControl;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.Media;
import org.iocaste.shell.common.StyleSheet;

public class BuilderCustomView extends AbstractCustomView
        implements ExtendedCustomView {
    private Map<ViewSpecItem.TYPES, SpecFactory> factories;
    private ViewConfig viewconfig;
    private ViewInput viewinput;
    private ViewSpec viewspec;
    
    public BuilderCustomView() {
        SpecFactory factory;
        
        factories = new HashMap<>();
        for (ViewSpecItem.TYPES type : ViewSpecItem.TYPES.values()) {
            factory = type.factory();
            if (factory != null);
                factories.put(type, factory);
        }
    }
    
    private void buildItem(PageBuilderContext context,
            ViewComponents components, ViewSpecItem item, String prefix) {
        SpecFactory factory;
        SpecItemHandler handler;
        
        factory = getFactory(item);
        if (factory == null)
            return;
        handler = factory.getHandler();
        if (handler != null)
            handler.execute(context, item.getName());
        factory.run(context, components, item, prefix);
    }
    
    private final void download(PageBuilderContext context) throws Exception {
        context.function.setContentType(
                context.downloaddata.contenttype);
        context.function.setContentEncoding(
                context.downloaddata.contentencoding);
        context.function.setHeader("Content-Disposition",
                new StringBuilder("attachment; filename=\"").
                append(context.downloaddata.filename).
                append("\"").toString());
        context.view.setContent(context.downloaddata.content);
    }
    
    @Override
    public void execute(PageBuilderContext context, ViewSpecItem itemspec,
            String prefix, boolean processparent) {
        ComponentEntry entry;
        SpecFactory factory;
        PageBuilderContext _context = (PageBuilderContext)context;
        ViewComponents components = new ViewComponents();
        
        viewspec.run(itemspec, _context);
        for (ViewSpecItem item : viewspec.getItems())
            if ((itemspec != item) || processparent)
                buildItem(_context, components, item, prefix);
        
        if (viewconfig != null)
            viewconfig.run(_context, prefix);
        
        if (viewinput != null)
            viewinput.run(_context, true, prefix);

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
        PageBuilderContext _context = (PageBuilderContext)context;
        
        if (_context.downloaddata != null) {
            download(_context);
            _context.downloaddata = null;
            return;
        }

        viewctx = _context.getView(getView());
        components = viewctx.getComponents();
        if (!viewspec.isInitialized()) {
            _context.view.clear();
            _context.function.unregisterValidators();
            viewspec.run(_context);
            viewctx.reset();
            
            for (ViewSpecItem item : viewspec.getItems())
                buildItem(_context, components, item, null);

            navcontrol = factories.get(ViewSpecItem.TYPES.PAGE_CONTROL).get();
            if (viewconfig != null) {
                extendStyleSheet(_context);
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
        } else {
            _context.refreshStyle();
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
    
    @SuppressWarnings("unchecked")
    private final void extendStyleSheet(PageBuilderContext context) {
        Map<String, Map<String, String>> sheet;
        String mediakey;
        Media media;

        context.stylesheet = StyleSheet.instance(context.view);
        for (int i = 0; i < context.ncsheet.length; i++) {
            mediakey = (String)context.ncsheet[i][0];
            media = context.stylesheet.instanceMedia(mediakey);
            media.setRule((String)context.ncsheet[i][1]);
            sheet = (Map<String, Map<String, String>>)context.ncsheet[i][2];
            context.stylesheet.add(mediakey, sheet);
        }
    }
    
    private final SpecFactory getFactory(ViewSpecItem item) {
        ViewSpecItem.TYPES[] types = ViewSpecItem.TYPES.values();
        
        return factories.get(types[item.getType()]);
    }
    
    @Override
    public final void setViewConfig(ViewConfig viewconfig) {
        this.viewconfig = viewconfig;
    }
    
    @Override
    public final void setViewInput(ViewInput viewinput) {
        this.viewinput = viewinput;
    }
    
    @Override
    public final void setViewSpec(ViewSpec viewspec) {
        this.viewspec = viewspec;
    }
}