import static org.junit.Assert.*;
import org.junit.*;	


public class PlotTestCase {
	/**
	 * Test case for the Plot class
	 * @author Ben
	 *
	 */
	private Plot pt;
	@Before
	public void setup(){
		pt = new Plot();		
	}
	
	@Test
	public void testCreatePlot(){
		Plot pt1 = new Plot(null,"ore",RoboticonCustomisation.UNCUSTOMISED);
		assertEquals(0,pt1.getPlayer());
		assertEquals("ore",pt1.getBest());
		assertEquals("none",pt1.getRoboticon());
	}
	@Test
	public void testSetBest(){
		pt.setBest("ore");
		String expectedBest = "ore";
		assertEquals(expectedBest,pt.getBest());
	}
	@Test
	public void testChangePlayer(){
		pt.setPlayer(1);
		int expectedPLayer = 1;
		assertEquals(expectedPLayer,pt.getPlayer());
	}
	@Test
	public void testAddRoboticon(){
		pt.setRoboticon("ore");
		String expectedRoboticon = "ore";
		assertEquals(expectedRoboticon,pt.getRoboticon());
	}
	@Test(expected=IllegalStateException.class)
	public void testPlotOwned(){
		pt.setPlayer(1);
		pt.setPlayer(2);
	}
	
}
