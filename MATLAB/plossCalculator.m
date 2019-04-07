function [ploss,avgPopulation] = plossCalculator
%PLOSSCALCULATOR Summary of this function goes here
%   Detailed explanation goes here
digits(11);
syms p00 p01 p02 p03 p10 p11 p12 p20 p21 p30 ploss
lambda1 = 6.00;
lambda2 = 6.25;
mu1 = 0.45;
mu2 = 0.27;
eqn1 = p00*(lambda1 + lambda2) == p01*mu2 + p10*mu1;
eqn2 = p01*(lambda1 + lambda2 + mu2) == p00*lambda2 + p11*mu1 + p02*(2*mu2);
eqn3 = p02*(lambda1 + lambda2 + 2*mu2) == p01*lambda2 + p12*mu1 + p03*(3*mu2);
eqn4 = p03*(3*mu2) == p02*(lambda2);
eqn5 = p10*(lambda1 + lambda2 + mu1) == p00*lambda1 + p11*mu2 + p20*(2*mu1);
eqn6 = p11*(mu1 + mu2 + lambda1 + lambda2) == p01*lambda1 + p10*lambda2 + p12*(2*mu2) + p21*(2*mu1);
eqn7 = p12*(mu1 + (2*mu2)) == p02*lambda1 + p11*lambda2;
eqn8 = p20*(lambda1 + lambda2 + (2*mu1)) == p10*lambda1 + p21*mu2 + p30*(3*mu1);
eqn9 = p21*((2*mu1) + mu2) == p11*lambda1 + p20*lambda2;
eqn10 = p30*(3*mu1) == p20*lambda1;
eqn11 = p00 + p01 + p02 + p03 + p10 + p11 + p12 + p20 + p21 + p30 == 1;
eqn12 = ploss == p30  + p12 + p21 + p03;
[A,B] = equationsToMatrix([eqn1, eqn2, eqn3, eqn4, eqn5, eqn6, eqn7, eqn8, eqn9, eqn10, eqn11, eqn12], [p00, p01, p02, p10, p11, p20, p21, p30, p03, p12, ploss]);
X=linsolve(A,B);
X=double(X);
p00 = X(1);
p01 = X(2);
p02 = X(3);
p10 = X(4);
p11 = X(5);
p20 = X(6);
p21 = X(7);
p30 = X(8);
p03 = X(9);
p12 = X(10);
ploss = X(11);
avgPopulation = 0*p00 + 1*(p01 + p10) + 2*(p20 + p02 + p11) + 3*(p30 + p03 + p12 + p21);
ploss = vpa(ploss);
avgPopulation = vpa(avgPopulation);
end

