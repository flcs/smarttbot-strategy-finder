package global;

import java.time.LocalTime;
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

	@Test
	public void localTime() {
		// Arrange
		Random random = Mockito.mock(Random.class);
		Mockito.when(random.nextInt(9)).thenReturn(4);
		Mockito.when(random.nextInt(60)).thenReturn(47);

		Whitebox.setInternalState(Rand.class, "random", random);

		// Act & Assert
		Assert.assertEquals(LocalTime.of(13, 47), Rand.getLocalTime(9, 17, 0, 59));
	}

}
