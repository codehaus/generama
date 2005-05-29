package org.generama.ant;

import org.apache.tools.ant.Project;
import org.generama.Generama;
import org.generama.OneTwoThreeStringMetadataProvider;
import org.generama.tests.SinkWriterMapper;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.nanocontainer.ant.PicoContainerTask;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.Startable;

/**
 * 
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class SimpleGeneramaTaskTestCase extends MockObjectTestCase {
    private PicoContainerTask task;
    private Mock mockPlugin = new Mock(Startable.class);

    public void setUp() {
        Project project = new Project();

        task = createPicoContainerTask();
        task.setProject(project);
        task.setTaskName(task.getClass().getName());
    }

    public void testExecutableMetadataProvider() {
        mockPlugin.expects(once()).method("start");
        mockPlugin.expects(once()).method("stop");
        task.execute();
        mockPlugin.verify();
    }

    protected PicoContainerTask createPicoContainerTask() {
        return new AbstractGeneramaTask() {
            protected Generama createGenerama() {
                Generama generama = new Generama(
                        OneTwoThreeStringMetadataProvider.class,
                        SinkWriterMapper.class
                ) {
                    public void composeContainer(MutablePicoContainer pico, Object assemblyScope) {
                        super.composeContainer(pico, assemblyScope);
                        pico.registerComponentInstance(mockPlugin.proxy());
                    }
                };
                return generama;
            }
        };
    }
}
