package io.documentnode.epub4j.domain;

import org.junit.Assert;
import org.junit.Test;

public class ResourcesTest {
	
	@Test
	public void testGetResourcesByMediaType1() {
		Resources resources = new Resources();
		resources.add(new Resource("foo".getBytes(), MediaTypes.XHTML));
		resources.add(new Resource("bar".getBytes(), MediaTypes.XHTML));
		Assert.assertEquals(0, resources.getResourcesByMediaType(MediaTypes.PNG).size());
		Assert.assertEquals(2, resources.getResourcesByMediaType(MediaTypes.XHTML).size());
		Assert.assertEquals(2, resources.getResourcesByMediaTypes(new MediaType[] {
        MediaTypes.XHTML}).size());
	}

	@Test
	public void testGetResourcesByMediaType2() {
		Resources resources = new Resources();
		resources.add(new Resource("foo".getBytes(), MediaTypes.XHTML));
		resources.add(new Resource("bar".getBytes(), MediaTypes.PNG));
		resources.add(new Resource("baz".getBytes(), MediaTypes.PNG));
		Assert.assertEquals(2, resources.getResourcesByMediaType(MediaTypes.PNG).size());
		Assert.assertEquals(1, resources.getResourcesByMediaType(MediaTypes.XHTML).size());
		Assert.assertEquals(1, resources.getResourcesByMediaTypes(new MediaType[] {
        MediaTypes.XHTML}).size());
		Assert.assertEquals(3, resources.getResourcesByMediaTypes(new MediaType[] {
        MediaTypes.XHTML, MediaTypes.PNG}).size());
		Assert.assertEquals(3, resources.getResourcesByMediaTypes(new MediaType[] {
        MediaTypes.CSS, MediaTypes.XHTML, MediaTypes.PNG}).size());
	}
}
