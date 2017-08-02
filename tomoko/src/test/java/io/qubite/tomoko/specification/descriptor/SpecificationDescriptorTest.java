package io.qubite.tomoko.specification.descriptor;

import io.qubite.tomoko.patch.CommandType;
import io.qubite.tomoko.patch.OperationDto;
import io.qubite.tomoko.specification.descriptor.value.UnaryValueHandlerDescriptor;
import io.qubite.tomoko.specification.descriptor.valueless.UnaryRemoveHandlerDescriptor;
import io.qubite.tomoko.specification.scanner.LinkingSpecification;
import io.qubite.tomoko.specification.scanner.TestSpecification;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SpecificationDescriptorTest {

    @Test
    public void generateOperation_directAddHandler() throws Exception {
        SpecificationDescriptor<TestSpecification> underTest = SpecificationDescriptor.forClass(TestSpecification.class);
        UnaryValueHandlerDescriptor<String, String> descriptor = underTest.addHandler(TestSpecification::updateTitle);
        OperationDto operation = descriptor.generate("ISBN", "new title");
        assertEquals(CommandType.ADD, operation.getType());
        assertEquals("/books/ISBN/title", operation.getPath());
    }

    @Test
    public void generateOperation_directReplaceHandler() throws Exception {
        SpecificationDescriptor<TestSpecification> underTest = SpecificationDescriptor.forClass(TestSpecification.class);
        UnaryValueHandlerDescriptor<String, String> descriptor = underTest.replaceHandler(TestSpecification::replaceTitle);
        OperationDto operation = descriptor.generate("ISBN", "new title");
        assertEquals(CommandType.REPLACE, operation.getType());
        assertEquals("/books/ISBN/title", operation.getPath());
    }

    @Test
    public void generateOperation_directRemoveHandler() throws Exception {
        SpecificationDescriptor<TestSpecification> underTest = SpecificationDescriptor.forClass(TestSpecification.class);
        UnaryRemoveHandlerDescriptor<String> descriptor = underTest.removeHandler(TestSpecification::removeTitle);
        OperationDto operation = descriptor.generate("ISBN");
        assertEquals(CommandType.REMOVE, operation.getType());
        assertEquals("/books/ISBN/title", operation.getPath());
    }

    @Test
    public void generateOperation_linkedAddHandler() throws Exception {
        SpecificationDescriptor<LinkingSpecification> underTest = SpecificationDescriptor.forClass(LinkingSpecification.class);
        UnaryValueHandlerDescriptor<String, String> descriptor = underTest.linked(LinkingSpecification::getLinked).addHandler(TestSpecification::updateTitle);
        OperationDto operation = descriptor.generate("ISBN", "new title");
        assertEquals(CommandType.ADD, operation.getType());
        assertEquals("/bookstore/books/ISBN/title", operation.getPath());
    }

}
