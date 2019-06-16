function responseTimeComparison()
digits(15);
M = readmatrix('..\RESULT_OUTPUT\ResponseTime.csv');

filteredMatrix = M(((M(:, 7) == 20)), :);
%algorithm seed server time
globalTime = filteredMatrix(:, [3 7 8]);

standardAlgorithm   = globalTime(globalTime(:, 1) == 1, [2 3]);
sizeBasedAlgorithm  = globalTime(globalTime(:, 1) == 2, [2 3]);
firstClassAlgorithm = globalTime(globalTime(:, 1) == 3, [2 3]);
tresholdAlgorithm   = globalTime(globalTime(:, 1) == 4, [2 3]);

SA3 = standardAlgorithm(standardAlgorithm(:, 1) == 3, 2);
SA20 = standardAlgorithm(standardAlgorithm(:, 1) == 20, 2);

SBA3 = sizeBasedAlgorithm(sizeBasedAlgorithm(:, 1) == 3, 2);
SBA20 = sizeBasedAlgorithm(sizeBasedAlgorithm(:, 1) == 20, 2);

FCA3 = firstClassAlgorithm(firstClassAlgorithm(:, 1) == 3, 2);
FCA20 = firstClassAlgorithm(firstClassAlgorithm(:, 1) == 20, 2);

TA3 = tresholdAlgorithm(tresholdAlgorithm(:, 1) == 3, 2);
TA20 = tresholdAlgorithm(tresholdAlgorithm(:, 1) == 20, 2);

a = 1:30;


figure;
plot(a, SA20(1:30, :));
hold on
plot(a, FCA20(1:30, :));
hold on 
plot(a, TA20(1:30, :));
%hold on 
%plot(a, SBA20(1:30, :));

%legend('standard', 'first class', 'treshold', 'size based')
legend('standard', 'first class', 'treshold')

title('global response time comparison');
xlabel('iteration');
ylabel('response time (s)');

end

