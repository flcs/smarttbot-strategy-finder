package global;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;

import eu.verdelhan.ta4j.Decimal;
import shell.global.Rand;

public class RandTest {

	@Test
	public void decimal() {
		// Arrange
		Random random = Mockito.mock(Random.class);
		Mockito.when(random.nextInt(451)).thenReturn(317);

		Whitebox.setInternalState(Rand.class, "random", random);

		// Act & Assert
		Assert.assertEquals(Decimal.valueOf("4.67"), Rand.getDecimal(150, 600, 100));
	}

}
