#!/usr/bin/env python2

from subprocess import Popen, PIPE
from jamcoin import Primes
import jamcoin
import pancakes
import sheep
import unittest

class Tests(unittest.TestCase):

  def test_jamcoin(self):
    assert jamcoin.is_jamcoin('100011')
    assert jamcoin.is_jamcoin('111111')
    assert jamcoin.is_jamcoin('111001')
    assert not jamcoin.is_jamcoin('110111')

  def test_pancakes(self):
    assert pancakes.make_smiley('-') == 1
    assert pancakes.make_smiley('-+') == 1
    assert pancakes.make_smiley('+-') == 2
    assert pancakes.make_smiley('+++++++-') == 2
    assert pancakes.make_smiley('+++') == 0
    assert pancakes.make_smiley('--+-') == 3
    assert pancakes.make_smiley('--+-') == 3
    assert self.output_matches('pancakes.py',
      'PancakeFiles/B-small-attempt0.in',
      'PancakeFiles/pancake-small.out')

  def test_pancakes_flip(self):
    assert pancakes.flip('-', 0) == '-'
    assert pancakes.flip('-', 1) == '+'
    assert pancakes.flip('+', 1) == '-'
    assert pancakes.flip('+-', 1) == '--'
    assert pancakes.flip('+-', 2) == '+-'
    assert pancakes.flip('--+-', 2) == '+++-'
    assert pancakes.flip('+++', 3) == '---'

  def test_primes(self):
    primes = Primes()
    assert 0 not in primes
    assert 1 not in primes
    taketen = []
    iprimes = iter(primes)
    for _ in range(10):
      taketen.append(next(iprimes))
    correct = [2, 3, 5, 7, 11, 13, 17, 19, 23, 29]
    assert taketen == correct
    assert 104744 not in primes
    assert 104743 in primes

  def test_sheep(self):
    assert sheep.count_sheep(0) == None
    assert sheep.count_sheep(1) == 10
    assert sheep.count_sheep(2) == 90
    assert sheep.count_sheep(11) == 110
    assert sheep.count_sheep(1692) == 5076
    assert self.output_matches('sheep.py',
      'SheepFiles/A-small-sheep-attempt1.in',
      'SheepFiles/small-sheep.out')

  '''
    Use the small files as varied test suites.
  '''
  def output_matches(self, filename, testin, testout):
    p = Popen(['python', filename], stdin=PIPE, stdout=PIPE)
    lines = open(testin).readlines()
    inlines = p.communicate(''.join(lines))[0]
    outlines = ''.join(open(testout).readlines())
    return inlines == outlines

if __name__ == '__main__':
  unittest.main()

