package com.company.Calculator;

public interface IOperand {
    IOperand add(IOperand other);
    IOperand subtract(IOperand other);
    IOperand multiply(IOperand other);
    IOperand divide(IOperand other);
}
